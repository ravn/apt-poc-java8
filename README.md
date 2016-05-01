autonomous preservation tools proof of concept for java 8

TODO:

* Rewrite newspaper tool launcher using BASJAVAD ideas with Dagger 2
  injection in mind.

* Implement autonomous tool as a Function<Item,Event>, with
  Stream<Item> and Consumer<Event> (but with Item and Event generic)

* Write sample tests showing workflow.


A task is responsible for handling a single Item, and store
corresponding events on the item (which in turn knows how to register
them in the repository), and return a value indicating to the caller
how it went.

As there is no atomic commit-rollback mechanism it is important to
design the workflow so it works correctly even if it takes time to add
several events to the item.

Exceptions must be caught (so an uncaught exception mean shut the tool
down).

