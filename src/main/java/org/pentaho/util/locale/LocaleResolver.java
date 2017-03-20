/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2017 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.util.locale;

import org.apache.log4j.Logger;

import java.util.Locale;

public class LocaleResolver {

    private static final Logger logger = Logger.getLogger( LocaleResolver.class );

    private static final ThreadLocal<Locale> threadLocales = new ThreadLocal<Locale>();
    private static final ThreadLocal<Locale> threadLocaleOverride = new ThreadLocal<Locale>();

    private static Locale defaultLocale;

    public static void setDefaultLocale( final Locale newLocale ) {
        LocaleResolver.defaultLocale = newLocale;
    }

    public static Locale getDefaultLocale() {
        return LocaleResolver.defaultLocale;
    }

    public static void setLocaleOverride( final Locale localeOverride ) {
        LocaleResolver.threadLocaleOverride.set( localeOverride );
    }

    public static Locale getLocaleOverride() {
        return LocaleResolver.threadLocaleOverride.get();
    }

    public static void setLocale( final Locale newLocale ) {
        LocaleResolver.threadLocales.set( newLocale );
    }

    public static Locale getLocale() {
        Locale override = LocaleResolver.threadLocaleOverride.get();
        if ( override != null ) {
            return override;
        }
        Locale rtn = LocaleResolver.threadLocales.get();
        if ( rtn != null ) {
            return rtn;
        }
        LocaleResolver.defaultLocale = Locale.getDefault();
        LocaleResolver.setLocale( LocaleResolver.defaultLocale );
        return LocaleResolver.defaultLocale;
    }
}
