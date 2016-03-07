/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package za.co.standardbank.sbg.cda.domain;

import java.util.List;

/**
 * <p>The main Configuration interface.</p>
 * <p>This interface allows accessing and manipulating a configuration object.
 * The major part of the methods defined in this interface deals with accessing
 * properties of various data types. There is a generic <code>getProperty()</code>
 * method, which returns the value of the queried property in its raw data
 * type. Other getter methods try to convert this raw data type into a specific
 * data type. If this fails, a <code>ConversionException</code> will be thrown.</p>
 * <p>For most of the property getter methods an overloaded version exists that
 * allows to specify a default value, which will be returned if the queried
 * property cannot be found in the configuration. The behavior of the methods
 * that do not take a default value in case of a missing property is not defined
 * by this interface and depends on a concrete implementation. E.g. the
 * <code>{@link AbstractConfiguration}</code> class, which is the base class
 * of most configuration implementations provided by this package, per default
 * returns <b>null</b> if a property is not found, but provides the
 * <code>{@link org.apache.commons.configuration.AbstractConfiguration#setThrowExceptionOnMissing(boolean)
 * setThrowExceptionOnMissing()}</code>
 * method, with which it can be configured to throw a <code>NoSuchElementException</code>
 * exception in that case. (Note that getter methods for primitive types in
 * <code>AbstractConfiguration</code> always throw an exception for missing
 * properties because there is no way of overloading the return value.)</p>
 * <p>With the <code>addProperty()</code> and <code>setProperty()</code> methods
 * new properties can be added to a configuration or the values of properties
 * can be changed. With <code>clearProperty()</code> a property can be removed.
 * Other methods allow to iterate over the contained properties or to create
 * a subset configuration.</p>
 *
 * @author Commons Configuration team
 * @version $Id: Configuration.java 449017 2006-09-22 17:27:00Z oheger $
 */
public interface Configuration
{
    /**
     * Gets a property from the configuration. This is the most basic get
     * method for retrieving values of properties. In a typical implementation
     * of the <code>Configuration</code> interface the other get methods (that
     * return specific data types) will internally make use of this method. On
     * this level variable substitution is not yet performed. The returned
     * object is an internal representation of the property value for the passed
     * in key. It is owned by the <code>Configuration</code> object. So a caller
     * should not modify this object. It cannot be guaranteed that this object
     * will stay constant over time (i.e. further update operations on the
     * configuration may change its internal state).
     *
     * @param key property to retrieve
     * @return the value to which this configuration maps the specified key, or
     *         null if the configuration contains no mapping for this key.
     */
    Object getProperty(String key);

    /**
     * Get the list of the keys contained in the configuration. The returned
     * iterator can be used to obtain all defined keys. Note that the exact
     * behavior of the iterator's <code>remove()</code> method is specific to
     * a concrete implementation. It <em>may</em> remove the corresponding
     * property from the configuration, but this is not guaranteed. In any case
     * it is no replacement for calling
     * <code>{@link #clearProperty(String)}</code> for this property. So it is
     * highly recommended to avoid using the iterator's <code>remove()</code>
     * method.
     *
     * @return An Iterator.
     */
    List getKeys();

    /**
     * Get a string associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated string.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     *         is not a String.
     */
    String getString(String key);
    
    boolean containsKey(String key);
}
