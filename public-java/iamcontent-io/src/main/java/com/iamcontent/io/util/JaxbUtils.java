/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
  @author Greg Elderfield, support@jarchitect.co.uk
 
  This program is free software; you can redistribute it and/or modify it under the terms of the
  GNU General Public License as published by the Free Software Foundation; either version 2 of 
  the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this program;
  if not, write to the Free Software Foundation, Inc., 
  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.iamcontent.io.util;

import static com.iamcontent.io.util.ResourceUtils.getStreamOrThrow;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.iamcontent.io.IORuntimeException;

/**
 * Utilities to help with XML marshalling/unmarshalling via JAXB.
 * @author Greg Elderfield
 */
public class JaxbUtils {

	public static <R> R unmarshalledFromFile(final String fileName, final Class<R> returnClass, Class<?>... boundClasses) {
		try (final InputStream is = getStreamOrThrow(fileName)) {
			return unmarshalled(is, returnClass, boundClasses);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
	
	public static <R> R unmarshalled(final InputStream is, final Class<R> returnClass, Class<?>... boundClasses) {
		try {
			final Unmarshaller unmarshaller = unmarshaller(append(boundClasses, returnClass));
			final Object result = unmarshaller.unmarshal(is);
			return returnClass.cast(result);
		} catch (JAXBException e) {
			throw new IORuntimeException(e);
		}
	}

	public static Unmarshaller unmarshaller(final Class<?>[] classes) throws JAXBException {
		final JAXBContext jc = JAXBContext.newInstance(classes);
		return jc.createUnmarshaller();
	}
	
	private static Class<?>[] append(Class<?>[] classes, final Class<?> c) {
		final int newLength = classes.length + 1;
		final Class<?>[] result = Arrays.copyOf(classes, newLength);
		result[newLength - 1] = c;
		return result;
	}
}
