# Stream Operations

> [!IMPORTANT]
> 
> This document aims to describe a subset of the available features based on a subjective selection.

## Intermediate operations

1. [Method `distinct()`](#method-distinct)
1. [Method `filter(predicate)`](#method-filterpredicate)
1. [Method `flatMap(mapper)`](#method-flatmapmapper)
1. [Method `limit(maxSize)`](#method-limitmaxsize)
1. [Method `map(mapper)`](#method-mapmapper)
1. [Method `peek(action)`](#method-peekaction)
1. [Method `skip(n)`](#method-skipn)
1. [Method `sorted()`](#method-sorted)
1. [Method `sorted(comparator)`](#method-sortedcomparator)

### Method `distinct()`

Returns a stream consisting of the distinct elements (according to `Object.equals(Object)`) of this stream.

Type: *stateful intermediate operation*

#### Signature

```java
Stream<T>
distinct()
```

#### Returns

the new stream

#### Examples

1. Printing each region to the standard output:

   ```java
   getAll()
      .stream()
      .map(Country::region)
      .distinct()
      .forEach(System.out::println);
   ```

### Method `filter(predicate)`

Returns a stream consisting of the elements of this stream that match the given predicate.

Type: *intermediate operation*

#### Signature

```java
Stream<T> 
filter(
   Predicate<? super T> predicate)
```

#### Parameters

1. `predicate` -  a non-interfering, stateless predicate to apply to each element to determine if it should be included

   type: `Predicate<? super T>`

#### Returns

the new stream

#### Examples

1. Printing each independent country to the standard output:

   ```java
   getAll()
      .stream()
      .filter(Country::independent)
      .forEach(System.out::println);
   ```

### Method `flatMap(mapper)`

Returns a stream consisting of the results of replacing each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element. Each mapped stream is `closed` after its contents have been placed into this stream. (If a mapped stream is `null` an empty stream is used, instead.)

Type: *intermediate operation*

#### Signature

```java
<R> 
Stream<R> 
flatMap(
   Function<? super T,? extends Stream<? extends R>> mapper)
```

#### Type parameters

* `R` - The element type of the new stream

#### Parameters

1. `mapper` -  a non-interfering, stateless function to apply to each element which produces a stream of new values

   type: `Function<? super T,? extends Stream<? extends R>>`

#### Returns

the new stream

#### Examples

1. Printing each timezone to the standard output.

   ```java
   getAll()
      .stream()
      .map(Country::timezones)
      .flatMap(List::stream)
      .distinct()
      .forEach(System.out::println);
   ```

### Method `limit(maxSize)`

Returns a stream consisting of the elements of this stream, truncated to be no longer than `maxSize` in length.

Type: *short-circuiting stateful intermediate operation*

#### Signature

```java
Stream<T>
limit(
   long maxSize)
```

#### Parameters

1. `maxSize` - the number of elements the stream should be limited to

   type: `long`

#### Returns

the new stream

#### Examples

1. Printing the first five countries to the standard output:

   ```java
   getAll()
      .stream()
      .limit(5)
      .forEach(System.out::println);
   ```

### Method `map(mapper)`

Returns a stream consisting of the results of applying the given function to the elements of this stream.

Type: *intermediate operation*

#### Signature

```java
<R> 
Stream<R>
map(
   Function<? super T,? extends R> mapper)
```

#### Type parameters

* `R` - The element type of the new stream

#### Parameters

1. `mapper` -  a non-interfering, stateless function to apply to each element

   type: `Function<? super T,? extends R>`

#### Returns

the new stream

#### Examples

1. Printing each country name to the standard output:

   ```java
   getAll()
      .stream()
      .map(Country::name)
      .forEach(System.out::println);
   ```

### Method `mapToDouble(mapper)`

Returns a `DoubleStream` consisting of the results of applying the given function to the elements of this stream.

Type: *intermediate operation*

#### Signature

```java
DoubleStream 
mapToDouble(
   ToDoubleFunction<? super T> mapper)
```

#### Parameters

1. `mapper` -  a non-interfering, stateless function to apply to each element

   type: `ToDoubleFunction<? super T>`

#### Returns

the new stream

#### Examples

1. Returning the statistics of the populations (in millions):

   ```java
   DoubleSummaryStatistics result = getAll()
      .stream()
      .mapToDouble(country -> country.population() / 1000000.0)
      .summaryStatistics();
   ```

### Method `mapToInt(mapper)`

Returns an `IntStream` consisting of the results of applying the given function to the elements of this stream.

Type: *intermediate operation*

#### Signature

```java
IntStream 
mapToInt(
   ToIntFunction<? super T> mapper)
```

#### Parameters

1. `mapper` -  a non-interfering, stateless function to apply to each element

   type: `ToIntFunction<? super T>`

#### Returns

the new stream

#### Examples

1. Returning the statistics of the hash-code values:

   ```java
   IntSummaryStatistics result = getAll()
      .stream()
      .mapToInt(Country::hashCode)
      .summaryStatistics()
   ```

### Method `mapToLong(mapper)`

Returns a `LongStream` consisting of the results of applying the given function to the elements of this stream.

Type: *intermediate operation*

#### Signature

```java
LongStream 
mapToLong(
   ToLongFunction<? super T> mapper)
```

#### Parameters

1. `mapper` -  a non-interfering, stateless function to apply to each element

   type: `ToLongFunction<? super T>`

#### Returns

the new stream

#### Examples

1. Returning the statistics of the population values:

   ```java
   LongSummaryStatistics result = getAll()
      .stream()
      .mapToLong(Country::population)
      .summaryStatistics();
   ```

### Method `peek(action)`

Returns a stream consisting of the elements of this stream, additionally performing the provided action on each element as elements are consumed from the resulting stream.

Type: *intermediate operation*

#### Signature

```java
Stream<T> 
peek(
   Consumer<? super T> action)
```

#### Parameters

1. `action` - a non-interfering action to perform on the elements as they are consumed from the stream

   type: `Consumer<? super T>`

#### Returns

the new stream

#### Examples

1. Printing each country to the standard output before counting them:

   ```java
   long result = getAll()
      .stream()
      .peek(System.out::println)
      .count();
   ```

### Method `skip(n)`

Returns a stream consisting of the remaining elements of this stream after discarding the first n elements of the stream. If this stream contains fewer than `n` elements then an empty stream will be returned.

Type: *stateful intermediate operation*

#### Signature

```java
Stream<T> 
skip(
   long n)
```

#### Parameters

1. `n` - the number of leading elements to skip

   type: `long`

#### Returns

the new stream

#### Examples

1. Printing each country to the standard output except the first five:

   ```java
   getAll()
      .stream()
      .skip(5)
      .forEach(System.out::println);
   ```

### Method `sorted()`

Returns a stream consisting of the elements of this stream, sorted according to natural order. If the elements of this stream are not `Comparable`, a `java.lang.ClassCastException` may be thrown when the terminal operation is executed.

Type: *stateful intermediate operation*

#### Signature

```java
Stream<T> 
sorted()
```

#### Returns

the new stream

#### Examples

1. Printing each country name to the standard output in alphabetical order:

   ```java
   getAll()
      .stream()
      .map(Country::name)
      .sorted()
      .forEach(System.out::println);
   ```

### Method `sorted(comparator)`

Returns a stream consisting of the elements of this stream, sorted according to the provided `Comparator`.

Type: *stateful intermediate operation*

#### Signature

```java
Stream<T> 
sorted(
   Comparator<? super T> comparator)
```

#### Parameters

1. `comparator` - a non-interfering, stateless Comparator to be used to compare stream elements

   type: `Comparator<? super T>`

#### Returns

the new stream

#### Returns

the new stream

#### Examples

1. Printing each country to the standard output in their names' alphabetical order:

   ```java
   getAll()
      .stream()
      .sorted(
         Comparator.comparing(
            Country::name
         )
      )
      .forEach(System.out::println);
   ``` 

## Terminal operations

1. [Method `allMatch(predicate)`](#method-allmatchpredicate)
1. [Method `anyMatch(predicate)`](#method-anymatchpredicate)
1. [Method `collect(collector)`](#method-collectcollector)
1. [Method `count()`](#method-count)
1. [Method `findFirst()`](#method-findfirst)
1. [Method `forEach(consumer)`](#method-foreachconsumer)
1. [Method `max(comparator)`](#method-maxcomparator)
1. [Method `min(comparator)`](#method-mmincomparator)
1. [Method `noneMatch(predicate)`](#method-nonematchpredicate)
1. [Method `toList()`](#method-tolist)

### Method `allMatch(predicate)`

Returns whether all elements of this stream match the provided predicate. May not evaluate the predicate on all elements if not necessary for determining the result. If the stream is empty then `true` is returned and the predicate is not evaluated.

Type: *short-circuiting terminal operation*

#### Signature

```java
boolean 
allMatch(
   Predicate<? super T> predicate)
```

#### Parameters

1. `predicate` - a non-interfering, stateless predicate to apply to elements of this stream

   type: `Predicate<? super T>`

#### Returns

`true` if either all elements of the stream match the provided predicate or the stream is empty, otherwise `false`

#### Examples

1. Returning whether each country is independent:

   ```java
   boolean result = getAll()
      .stream()
      .allMatch(Country::independent);
   ```
 
### Method `anyMatch(predicate)`

Returns whether any elements of this stream match the provided predicate. May not evaluate the predicate on all elements if not necessary for determining the result. If the stream is empty then `false` is returned and the predicate is not evaluated.

Type: *short-circuiting terminal operation*

#### Signature

```java
boolean 
anyMatch(
   Predicate<? super T> predicate)
```

#### Parameters

1. `predicate` - a non-interfering, stateless predicate to apply to elements of this stream

   type: `Predicate<? super T>`

#### Returns

`true` if any elements of the stream match the provided predicate, otherwise `false`

#### Examples

1. Returning whether at least one country is independent:

   ```java
   boolean result = getAll()
      .stream()
      .any(Country::independent);
   ```

### Method `collect(collector)`

Performs a mutable reduction operation on the elements of this stream using a Collector. A Collector encapsulates the functions used as arguments to collect(`Supplier`, `BiConsumer`, `BiConsumer`), allowing for reuse of collection strategies and composition of collect operations such as multiple-level grouping or partitioning.

Type: *terminal operation*

#### Signature

```java
<R,A> 
R 
collect(
   Collector<? super T,A,R> collector)
```

#### Type parameters

* `R` - the type of the result
* `A` - the intermediate accumulation type of the `Collector`

#### Parameters

1. `collector` - the Collector describing the reduction

   type: `Collector<? super T,A,R>`

#### Returns

the result of the reduction

#### Examples

1. Returning the set of countries:

   ```java
   Set<Country> result = getAll()
      .stream()
      .collect(
            Collectors.toSet()
      );
   ```

### Method `count()`

Returns the count of elements in this stream.

Type: *terminal operation*

#### Signature

```java
long 
count()
```

#### Returns

the count of elements in this stream

#### Examples

1. Returning the number of countries:

   ```java
   long result = getAll()
      .stream()
      .count();
   ```

### Method `findFirst()`

Returns an `Optional` describing the first element of this stream, or an empty `Optional` if the stream is empty. If the stream has no encounter order, then any element may be returned.

Type: *short-circuiting terminal operation*

#### Signature

```java
Optional<T> 
findFirst()
```
#### Returns

an `Optional` describing the first element of this stream, or an empty `Optional` if the stream is empty

#### Examples

1. Returning the first independent country:

   ```java
   Optional<Country> result = getAll()
      .stream()
      .filter(Country::independent)
      .findFirst();
   ```

### Method `forEach(consumer)`

Performs an action for each element of this stream.

Type: *terminal operation*

#### Signature

```java
void 
forEach(
   Consumer<? super T> action)
```

#### Parameters

1. `action` - a non-interfering action to perform on the elements

   type: `Consumer<? super T>`

#### Examples

1. Printing each country to the standard output:

   ```java
   getAll()
      .stream()
      .forEach(System.out::println);
   ```

### Method `max(comparator)`

Returns the maximum element of this stream according to the provided `Comparator`. This is a special case of a reduction.

Type: *terminal operation*

#### Signature

```java
Optional<T> 
max(
   Comparator<? super T> comparator)
```

#### Parameters

1. `comparator` - a non-interfering, stateless `Comparator` to compare elements of this stream

   type: `Comparator<? super T>`

#### Returns

an `Optional` describing the maximum element of this stream, or an empty `Optional` if the stream is empty

#### Examples

1. Printing the country having the last name in the alphabetical order:

   ```java
   Optional<Country> result = getAll()
      .stream()
      .max(
            Comparator.comparing(
                     Country::name
            )
      );
   ```

### Method `min(comparator)`

Returns the minimum element of this stream according to the provided `Comparator`. This is a special case of a reduction.

Type: *terminal operation*

#### Signature

```java
Optional<T> 
min(
   Comparator<? super T> comparator)
```

#### Parameters

1. `comparator` - a non-interfering, stateless `Comparator` to compare elements of this stream

   type: `Comparator<? super T>`

#### Returns

an `Optional` describing the maximum element of this stream, or an empty `Optional` if the stream is empty

#### Examples

1. Printing the country having the first name in the alphabetical order:

   ```java
   Optional<Country> result = getAll()
      .stream()
      .min(
            Comparator.comparing(
                     Country::name
            )
      );
   ```

### Method `noneMatch(predicate)`

Returns whether no elements of this stream match the provided predicate. May not evaluate the predicate on all elements if not necessary for determining the result. If the stream is empty then `true` is returned and the predicate is not evaluated.

Type: *short-circuiting terminal operation*

#### Signature

```java
boolean 
noneMatch(
   Predicate<? super T> predicate)
```

#### Parameters

1. `predicate` - a non-interfering, stateless predicate to apply to elements of this stream

   type: `Predicate<? super T>`

#### Returns

`true` if either no elements of the stream match the provided predicate or the stream is empty, otherwise false

#### Examples

1. Returning whether none of the countries is independent:

   ```java
   boolean result = getAll()
      .stream()
      .noneMatch(Country::independent);
   ```

### Method `toList()`

Accumulates the elements of this stream into a `List`. The elements in the list will be in this stream's encounter order, if one exists. The returned `List` is unmodifiable; calls to any mutator method will always cause `UnsupportedOperationException` to be thrown. There are no guarantees on the implementation type or serializability of the returned `List`.

Type: *terminal operation*

#### Signature

```java
default 
List<T> 
toList()
```

#### Returns

a `List` containing the stream elements

#### Examples

1. Returning the unmodifiable list of countries:

   ```java
   List<Country> result = getAll()
      .stream()
      .toList();
   ```

## Primitive streams

### Interface [`IntStream`](https://docs.oracle.com/en/java/javase/21//docs/api/java.base/java/util/stream/IntStream.html)

A sequence of primitive `int`-valued elements supporting sequential and parallel aggregate operations. This is the `int` primitive specialization of `Stream`.

#### Featured Methods

1. Method `OptionalDouble average()`

   Returns an `OptionalDouble` describing the arithmetic mean of elements of this stream, or an empty optional if this stream is empty.

1. Method `Stream<Integer> boxed()`

   Returns a `Stream` consisting of the elements of this stream, each boxed to an `Integer`.

1. Method `OptionalInt max()`

   Returns an `OptionalInt` describing the maximum element of this stream, or an empty optional if this stream is empty.

1. Method `OptionalInt min()`

   Returns an `OptionalInt` describing the minimum element of this stream, or an empty optional if this stream is empty.

1. Method `static IntStream range(int startInclusive, int endExclusive)`

   Returns a sequential ordered `IntStream` from `startInclusive` (inclusive) to `endExclusive` (exclusive) by an incremental step of `1`.

1. Method `static IntStream rangeClosed(int startInclusive, int endInclusive)`

   Returns a sequential ordered `IntStream` from `startInclusive` (inclusive) to `endInclusive` (inclusive) by an incremental step of `1`.

1. Method `OptionalInt sum()`

   Returns the sum of elements in this stream.

1. Method `IntSummaryStatistics summaryStatistics()`

   Returns an `IntSummaryStatistics` describing various summary data about the elements of this stream.


### Interface [`LongStream`](https://docs.oracle.com/en/java/javase/21//docs/api/java.base/java/util/stream/LongStream.html)

A sequence of primitive `long`-valued elements supporting sequential and parallel aggregate operations. This is the `long` primitive specialization of `Stream`.

#### Featured Methods

1. Method `OptionalDouble average()`

   Returns an `OptionalDouble` describing the arithmetic mean of elements of this stream, or an empty optional if this stream is empty.

1. Method `Stream<Long> boxed()`

   Returns a `Stream` consisting of the elements of this stream, each boxed to an `Long`.

1. Method `OptionalLong max()`

   Returns an `OptionalLong` describing the maximum element of this stream, or an empty optional if this stream is empty.

1. Method `OptionalLong min()`

   Returns an `OptionalLong` describing the minimum element of this stream, or an empty optional if this stream is empty.

1. Method `OptionalLong sum()`

   Returns the sum of elements in this stream.

1. Method `LongSummaryStatistics summaryStatistics()`

   Returns a `LongSummaryStatistics` describing various summary data about the elements of this stream.

### Interface [`DoubleStream`](https://docs.oracle.com/en/java/javase/21//docs/api/java.base/java/util/stream/DoubleStream.html)

A sequence of primitive `double`-valued elements supporting sequential and parallel aggregate operations. This is the `double` primitive specialization of `Stream`.

#### Featured Methods

1. Method `OptionalDouble average()`

   Returns an `OptionalDouble` describing the arithmetic mean of elements of this stream, or an empty optional if this stream is empty.

1. Method `Stream<Double> boxed()`

   Returns a `Stream` consisting of the elements of this stream, each boxed to an `Double`.

1. Method `OptionalDouble max()`

   Returns an `OptionalDouble` describing the maximum element of this stream, or an empty optional if this stream is empty.

1. Method `OptionalDouble min()`

   Returns an `OptionalDouble` describing the minimum element of this stream, or an empty optional if this stream is empty.

1. Method `OptionalDouble sum()`

   Returns the sum of elements in this stream.

1. Method `DoubleSummaryStatistics summaryStatistics()`

   Returns a `DoubleSummaryStatistics` describing various summary data about the elements of this stream.

## Static and default methods of interface `Comparator`

### Method `static comparing(keyExtractor)`

Accepts a function that extracts a `Comparable` sort key from a type `T`, and returns a `Comparator<T>` that compares by that sort key.

#### Signature

```java
static <T,U extends Comparable<? super U>>
Comparator<T> 
comparing(
   Function<? super T,? extends U> keyExtractor)
```

#### Type parameters

* `T` - the type of element to be compared
* `U` - the type of the `Comparable` sort key

#### Parameters

1. `keyExtractor` - the function used to extract the `Comparable` sort key

   type: `Function<? super T,? extends U>`

#### Returns

a comparator that compares by an extracted key

#### Examples

1. Returning the ordered list of countries by their names in ascending order:

   ```java
   List<Country> result = getAll()
      .stream()
      .sorted(
         Comparator
            .comparing(Country::region, Comparator.reverseOrder())
            .thenComparing(Country::name)
      )
      .toList();
   ```

### Method `static comparing(keyExtractor, keyComparator)`

Accepts a function that extracts a sort key from a type `T`, and returns a `Comparator<T>` that compares by that sort key using the specified `Comparator`.

#### Signature

```java
static <T,U> 
Comparator<T> 
comparing(
   Function<? super T,? extends U> keyExtractor,
   Comparator<? super U> keyComparator)
```

#### Type parameters

* `T` - the type of element to be compared
* `U` - the type of the sort key

#### Parameters

1. `keyExtractor` - the function used to extract the sort key

   type: `Function<? super T,? extends U>`

1. `keyComparator` - the `Comparator` used to compare the sort key

   type: `Comparator<? super U>`

#### Returns

a comparator that compares by an extracted key using the specified `Comparator`

#### Examples

1. Returning the ordered list of countries using the following stages:

   1. region of the country (descending)
   1. name of the country (ascending)

   ```java
   List<Country> result = getAll()
      .stream()
      .sorted(
         Comparator
            .comparing(Country::region, Comparator.reverseOrder())
            .thenComparing(Country::name)
      )
      .toList();
   ```

1. Returning the ordered list of countries using the following stages:

   1. number of timezones (descending)
   1. name of the country (ascending)

   ```java
   List<Country> result = getAll()
      .stream()
      .sorted(
         Comparator
            .comparing(
               (Country country) -> country.timezones().size(),
               Comparator.reverseOrder()
            )
            .thenComparing(Country::name)
      )
      .toList();
   ```

### Method `default thenComparing(other)`

Returns a lexicographic-order comparator with another comparator.

#### Signature

```java
default 
Comparator<T> 
thenComparing(
   Comparator<? super T> other)
```

#### Parameters

1. `other` - the other comparator to be used when this comparator compares two objects that are equal

   type: `Comparator<T>`

#### Returns

a lexicographic-order comparator composed of this and then the other comparator

#### Examples

1. Returning the ordered list of countries using the following stages:

   1. region of the country (ascending)
   1. name of the country (ascending)

   ```java
   List<Country> result = getAll()
      .stream()
      .sorted(
         Comparator
            .comparing(Country::region)
            .thenComparing(Country::name)
      )
      .toList();
   ```

### Method `default thenComparing(keyExtractor, keyComparator)`

Returns a lexicographic-order comparator with a function that extracts a key to be compared with the given `Comparator`.

#### Signature

```java
default <U> 
Comparator<T> 
thenComparing(
   Function<? super T,? extends U> keyExtractor,
   Comparator<? super U> keyComparator)
```

#### Type parameters

* `U` - the type of the sort key

#### Parameters

1. `keyExtractor` - the function used to extract the sort key

   type: `Function<? super T,? extends U>`

1. `keyComparator` - the `Comparator` used to compare the sort key

   type: `Comparator<? super U>`

#### Returns

a lexicographic-order comparator composed of this comparator and then comparing on the key extracted by the keyExtractor function

#### Examples

1. Returning the ordered list of countries using the following stages:

   1. region of the country (ascending)
   1. name of the country (descending)

   ```java
   List<Country> result = getAll()
      .stream()
      .sorted(
         Comparator
            .comparing(Country::region)
            .thenComparing(Country::name, Comparator.reverseOrder())
      )
      .toList();
   ```

### Method `static naturalOrder()`

Returns a comparator that compares `Comparable` objects in natural order.

#### Signature

```java
static <T extends Comparable<? super T>>
Comparator<T> 
naturalOrder()
```

#### Type parameters

* `T` - the Comparable type of element to be compared

#### Returns

a comparator that imposes the natural ordering on `Comparable` objects.

#### Examples

1. Returning the ordered list of countries names in ascending order:

   ```java
   List<String> result = getAll()
      .stream()
      .map(Country::name)
      .sorted(
         Comparator.naturalOrder()
      )
      .toList();
   ```

### Method `static nullsFirst()`

Returns a `null`-friendly comparator that considers `null` to be less than non-`null`.

#### Signature

```java
static <T> 
Comparator<T> 
nullsFirst(
   Comparator<? super T> comparator)
```

#### Type parameters

* `T` - the type of the elements to be compared

#### Parameters

1. `comparator` - a `Comparator` for comparing non-null values

   type: `Comparator<? super T>`

#### Returns

a comparator that considers `null` to be less than non-`null`, and compares non-`null` objects with the supplied `Comparator`

#### Examples

1. Returning the ordered list of capitals, assigning the `null` values to the first positions:

   ```java
   List<String> result = getAll()
      .stream()
      .map(Country::capital)
      .sorted(
         Comparator.nullsFirst(
            Comparator.naturalOrder()
         )
      )
      .toList();
   ```

### Method `static nullsLast()`

Returns a `null`-friendly comparator that considers `null` to be greater than non-`null`.

#### Signature

```java
static <T> 
Comparator<T> 
nullsLast(
   Comparator<? super T> comparator)
```

#### Type parameters

* `T` - the type of the elements to be compared

#### Parameters

1. `comparator` - a `Comparator` for comparing non-null values

   type: `Comparator<? super T>`

#### Returns

a comparator that considers `null` to be greater than non-`null`, and compares non-`null` objects with the supplied `Comparator`

#### Examples

1. Returning the ordered list of capitals, assigning the `null` values to the last positions:

   ```java
   List<String> result = getAll()
      .stream()
      .map(Country::capital)
      .sorted(
         Comparator.nullsLast(
            Comparator.naturalOrder()
         )
      )
      .toList();
   ```

### Method `static reverseOrder()`

Returns a comparator that imposes the reverse of the natural ordering.

#### Signature

```java
static <T extends Comparable<? super T>>
Comparator<T>
reverseOrder()
```

#### Type parameters

* `T` - the type of the elements to be compared

#### Returns

a comparator that imposes the reverse of the natural ordering on `Comparable` objects

#### Examples

1. Returning the ordered list of countries names in descending order:

   ```java
   List<String> result = getAll()
      .stream()
      .map(Country::name)
      .sorted(
         Comparator.reverseOrder()
      )
      .toList();
   ```