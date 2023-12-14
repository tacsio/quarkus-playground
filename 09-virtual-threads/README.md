# virtual-threads

[Quarkus IO](https://quarkus.io/blog/virtual-threads-2/)

The application uses:

RESTEasy Reactive - the recommended REST stack for Quarkus. It supports virtual threads.

Hibernate Validation - to validate the Todos created by the user.

Hibernate ORM with Panache - to interact with the database.

The Argroal connection pool - to manage and recycle database connections.

The Narayana transaction manager - to run our code inside transactions.

The PostgreSQL driver - as we use a PostgreSQL database

The code is similar to a regular implementation of a CRUD service with Quarkus, except for one line. We added the @RunOnVirtualThread annotation on the resource class (line 17). It instructs Quarkus to invoke these methods on virtual threads instead of regular platform threads (learn more about the difference in the previous blog post), including @Transactional methods.

The threading model
As we have seen in the code, the development model is synchronous. The interactions with the database uses blocking APIs: you wait for the replies. That’s where virtual thread introduces their magic. Instead of blocking a platform thread, it only blocks the virtual threads:

Threading model of the application
Thus, when another request comes, the carrier thread can handle it. It radically reduces the number of platform threads required when there are many concurrent requests. As a result, the number of worker threads, generally used when using a synchronous and blocking development model, is not the bottleneck anymore.

However, that’s not because you use virtual threads that your application has no more concurrency limit. There is a new bottleneck: the database connection pool. When you interact with the database, you ask for a connection to the connection pool (Agroal in our case). The number of connections is not infinite (20 by default). Once all the connections are used, you must wait until another processing completes and releases its connection. You can still handle many requests concurrently, but they will wait for database connections to be available, reducing the response time.

A note about pinning
As the previous blog post described, pinning happens when the virtual thread cannot be unmounted from the carrier thread. In this case, blocking the virtual thread also blocks the carrier thread:

Pinning of the carrier thread
Fortunately, in this application, there is no pinning. The PostgreSQL driver is one of the only JDBC drivers that does not pin. If you plan to use another database, check first. We will be discussing how to detect pinning in the next post. Quarkus, Narayana and Hibernate have been patched to avoid the pinning.

Pinning is one of many problems that can arise. The application will suffer from the default object pooling mechanism used by Jackson. Fortunately, we contributed an SPI to Jackson that will allow us to remove this allocation hog.