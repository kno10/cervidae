/**
 * <p>C++ style Iterator API.</p>
 * 
 * <p>Cervidae uses a custom iterator API instead of the usual {@link java.util.Iterator} classes (the "Java Collections API").
 * The reason for this is largely efficiency. Benchmarking showed that the Java Iterator API can be quite expensive when dealing
 * with primitive types, as {@link java.util.Iterator#next} is meant to always return an object.</p>
 * 
 * <p>However, the benefits become more apparent when considering multi-valued iterators.</p>
 * 
 * <p>While it may seem odd to depart from Java conventions such as the collections API,
 * note that these iterators are very close to the standard C++ conventions, so nothing entirely unusual.
 * Also the GNU trove libraries use the same kind of iterators.</p> 
 */
package com.kno10.java.cervidae.iterator;