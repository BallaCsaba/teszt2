
# The `Collector` interface

> [!IMPORTANT]
> 
> This document aims to describe a subset of the available features based on a subjective selection.

## Static factory methods of the `Collector` interface

### Method `of(supplier, accumulator, combiner, characteristics)`

Returns a new `Collector` described by the given supplier, accumulator, and combiner functions. The resulting `Collector` has the `Collector.Characteristics.IDENTITY_FINISH` characteristic.

#### Signature

```java
static <T,R>
Collector<T,R,R>
of(
   Supplier<R> supplier,
   BiConsumer<R,T> accumulator,
   BinaryOperator<R> combiner,
   Collector.Characteristics... characteristics)
```

#### Type parameters

* `T` - The type of input elements for the new collector
* `R` - The type of intermediate accumulation result, and final result, for the new collector

#### Parameters

1. `supplier` - The supplier function for the new collector

   type: `Supplier<R>`

1. `accumulator` - The accumulator function for the new collector

   type: `BiConsumer<R,T>`

1. `combiner` - The combiner function for the new collector

   type: `BinaryOperator<R>`

1. `characteristics` - The collector characteristics for the new collector

   type: `Collector.Characteristics...`

#### Returns

the new `Collector`

#### Examples

1. Returning a `StringJoiner` instance that joins the country names by commas:

   ```java
   StringJoiner result = getAll()
      .stream()
      .map(Country::name)
      .collect(
         Collector.of(
            () -> new StringJoiner(","),
            StringJoiner::add,
            StringJoiner::merge
         )
      );
   ```

### Method `of(supplier, accumulator, combiner, finisher, characteristics)`

Returns a new `Collector` described by the given supplier, accumulator, combiner, and finisher functions.


#### Signature

```java
static <T,R>
Collector<T,R,R>
of(
   Supplier<R> supplier,
   BiConsumer<R,T> accumulator,
   BinaryOperator<R> combiner,
   Function<A,R> finisher,
   Collector.Characteristics... characteristics)
```

#### Type parameters

* `T` - The type of input elements for the new collector
* `A` - The intermediate accumulation type of the new collector
* `R` - The final result type of the new collector

#### Parameters

1. `supplier` - The supplier function for the new collector

   type: `Supplier<A>`

1. `accumulator` - The accumulator function for the new collector

   type: `BiConsumer<A,T>`

1. `combiner` - The combiner function for the new collector

   type: `BinaryOperator<A>`

1. `finisher` - The finisher function for the new collector

   type: `Function<A,R>`

1. `characteristics` - The collector characteristics for the new collector

   type: `Collector.Characteristics...`

#### Returns

the new `Collector`

#### Examples

1. Returning a `String` containing the country names separated by commas:

   ```java
   String result = getAll()
      .stream()
      .map(Country::name)
      .collect(
         Collector.of(
            () -> new StringJoiner(","),
            StringJoiner::add,
            StringJoiner::merge,
            StringJoiner::toString
         )
      );
   ```

## Static `Collectors` methods for collecting into `List` instances

### Method `toList()`

Returns a `Collector` that accumulates the input elements into a new `List`. There are no guarantees on the type, mutability, serializability, or thread-safety of the `List` returned; if more control over the returned `List` is required, use toCollection(Supplier).

#### Signature

```java
public static <T>
Collector<T,?,List<T>>
toList()
```

#### Type parameters

* `T` - the type of the input elements

#### Returns

a `Collector` which collects all the input elements into a `List`, in encounter order

#### Examples

1. Collecting the countries into a `List`:

   ```java
   List<Country> result = getAll()
      .stream()
      .collect(
         Collectors.toList()
      );
   ```

### Method `toUnmodifiableList()`

Returns a `Collector` that accumulates the input elements into an unmodifiable `List` in encounter order. The returned `Collector` disallows `null` values and will throw NullPointerException if it is presented with a `null` value.

#### Signature

```java
public static <T> 
Collector<T,?,List<T>> 
toUnmodifiableList()
```

#### Type parameters

* `T` - the type of the input elements

#### Returns

a `Collector` that accumulates the input elements into an unmodifiable `List` in encounter order

#### Examples

1. Collecting the countries into an unmodifiable `List`:

   ```java
   List<Country> result = getAll()
      .stream()
      .collect(
         Collectors.toUnmodifiableList()
      );

## Static `Collectors` methods for collecting into `Set` instances

### Method `toSet()`

Returns a `Collector` that accumulates the input elements into a new `Set`. There are no guarantees on the type, mutability, serializability, or thread-safety of the `Set` returned; if more control over the returned `Set` is required, use `toCollection(Supplier)`.

#### Signature

```java
public static <T> 
Collector<T,?,Set<T>> 
toSet()
```

#### Type parameters

* `T` - the type of the input elements

#### Returns

a `Collector` which collects all the input elements into a `Set`

#### Examples

1. Collecting the countries into a `Set`:

   ```java
   Set<Country> result = getAll()
      .stream()
      .collect(
         Collectors.toSet()
      );
   ```


### Method `toUnmodifiableSet()`

Returns a `Collector` that accumulates the input elements into an unmodifiable `Set`. The returned `Collector` disallows `null` values and will throw `NullPointerException` if it is presented with a `null` value. If the input contains duplicate elements, an arbitrary element of the duplicates is preserved.

#### Signature

```java
public static <T> 
Collector<T,?,Set<T>> 
toUnmodifiableSet()
```

#### Type parameters

* `T` - the type of the input elements

#### Returns

a `Collector` that accumulates the input elements into an unmodifiable `Set`

#### Examples

1. Collecting the countries into an unmodifiable `Set`:

   ```java
   Set<Country> result = getAll()
      .stream()
      .collect(
         Collectors.toUnmodifiableSet()
      );
   ```

## Static `Collectors` methods for collecting into `Map` instances

### Method `toMap(keyMapper, valueMapper)`

Returns a `Collector` that accumulates elements into a `Map` whose keys and values are the result of applying the provided mapping functions to the input elements.

#### Signature

```java
public static <T,K,U> 
Collector<T,?,Map<K,U>> 
toMap(
   Function<? super T,? extends K> keyMapper,
   Function<? super T,? extends U> valueMapper)
```

#### Type parameters

* `T` - the type of the input elements
* `K` - the output type of the key mapping function
* `U` - the output type of the value mapping function

#### Parameters

1. `keyMapper` - a mapping function to produce keys

   type: `Function<? super T,? extends K>`

1. `valueMapper` - a mapping function to produce values

   type: `Function<? super T,? extends U>`

#### Returns

a `Collector` which collects elements into a `Map` whose keys and values are the result of applying mapping functions to the input elements

#### Examples

1. Mapping each code to the corresponding capital:

   ```java
   Map<String, String> result = getAll()
      .stream()
      .collect(
         Collectors.toMap(
            Country::code,
            Country::capital
         )
      );
   ```


1. Mapping each code to the corresponding coutry:

   ```java
   Map<String, Country> result = getAll()
      .stream()
      .collect(
         Collectors.toMap(
            Country::code,
            Function.identity()
         )
      );
   ```

### Method `groupingBy(classifier)`

Returns a `Collector` implementing a "group by" operation on input elements of type `T`, grouping elements according to a classification function, and returning the results in a `Map`.

#### Signature

```java
public static <T,K> 
Collector<T,?,Map<K,List<T>>> 
groupingBy(
   Function<? super T,? extends K> classifier)
```

#### Type parameters

* `T` - the type of the input elements
* `K` - the type of the keys

#### Parameters

1. `classifier` - a classifier function mapping input elements to keys

   type: `Function<? super T,? extends K>`

#### Returns

a `Collector` implementing the group-by operation

#### Examples

1. Mapping each region to the `List` of corresponding countries:

   ```java
   Map<Region, List<Country>> result = getAll()
      .stream()
      .collect(
         Collectors.groupingBy(
            Country::region
         )
      );
   ```

### Method `groupingBy(classifier, downstream)`

Returns a `Collector` implementing a "group by" operation on input elements of type `T`, grouping elements according to a classification function, and returning the results in a `Map`.

#### Signature

```java
public static <T,K,A,D> 
Collector<T,?,Map<K,D>> 
groupingBy(
   Function<? super T,? extends K> classifier,
   Collector<? super T,A,D> downstream)
```

#### Type parameters

* `T` - the type of the input elements
* `K` - the type of the keys
* `A` - the intermediate accumulation type of the downstream collector
* `D` - the result type of the downstream reduction

#### Parameters

1. `classifier` - a classifier function mapping input elements to keys

   type: `Function<? super T,? extends K>`

1. `downStream` - a `Collector` implementing the downstream reduction

   type: `Collector<? super T,A,D> downstream`

#### Returns

a `Collector` implementing the group-by operation

#### Examples

1. Mapping each region to the number of corresponding countries:

   ```java
   Map<Region, Long> result = getAll()
      .stream()
      .collect(
         Collectors.groupingBy(
            Country::region,
            Collectors.counting()
         )
      );
   ```

1. Mapping each region to the `Set` of corresponding countries:

   ```java
   Map<Region, Set<Country>> result = getAll()
      .stream()
      .collect(
         Collectors.groupingBy(
            Country::region,
            Collectors.toSet()
         )
      );
   ```

## Other methods of the `Collectors` class

### Method `counting()`

Returns a `Collector` accepting elements of type `T` that counts the number of input elements. If no elements are present, the result is `0`.

#### Signature

```java
public static <T> 
Collector<T,?,Long> 
counting()
```

#### Type parameters:

* `T` - the type of the input elements

#### Examples

1. Mapping each region to the number of corresponding countries:

   ```java
   Map<Region, Long> result = getAll()
      .stream()
      .collect(
         Collectors.groupingBy(
            Country::region,
            Collectors.counting()
         )
      );
   ```

### Method `joining(delimiter)`

Returns a `Collector` that concatenates the input elements, separated by the specified delimiter, in encounter order.

#### Signature

```java
public static 
Collector<CharSequence,?,String> 
joining(
   CharSequence delimiter)
```

#### Parameters

1. `delimiter` - the delimiter to be used between each element

   type: `CharSequence`

#### Examples

1. Concatenating the country names using the `,` delimiter:

   ```java
   String result = getAll()
      .stream()
      .map(Country::name)
      .sorted()
      .collect(Collectors.joining(","));
   ```

### Method `collectingAndThen(downstream, finisher)`

Adapts a `Collector` to perform an additional finishing transformation. For example, one could adapt the `toList()` collector to always produce an immutable list.

#### Signature

```java
public static <T,A,R,RR>
Collector<T,A,RR>
collectingAndThen(
   Collector<T,A,R> downstream,
   Function<R,RR> finisher)
```

#### Type parameters:

* `T` - the type of the input elements
* `A` - intermediate accumulation type of the downstream collector
* `R` - result type of the downstream collector
* `RR` - result type of the resulting collector

#### Parameters

1. `downstream` - a collector

   type: `Collector<T,A,R>`

1. `finisher` - a function to be applied to the final result of the downstream collector

   type: `Function<R,RR>`

#### Returns

a collector which performs the action of the downstream collector, followed by an additional finishing step

#### Examples

1. Mapping each region to the sorted list of corresponding country names:

   ```java
   Map<Region, List<String>> result = getAll()
      .stream()
      .collect(
         Collectors.groupingBy(
         Country::region,
         Collectors.collectingAndThen(
            Collectors.toList(),
            countries -> countries
               .stream()
               .sorted(
                  Comparator.comparing(
                     Country::name
                  )
               )
               .toList()
         )
      ));
   ```
