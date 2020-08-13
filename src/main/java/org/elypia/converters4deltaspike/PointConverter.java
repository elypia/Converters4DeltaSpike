/*
 * Copyright 2020-2020 Elypia CIC and Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.elypia.converters4deltaspike;

import org.apache.deltaspike.core.api.config.ConfigResolver;

import java.awt.Point;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author seth@elypia.org (Seth Falco)
 * @since 1.1.0
 */
public class PointConverter implements ConfigResolver.Converter<Point> {
    /** Pattern used to split the {@link Point#x} and {@link Point#y} coordinate. */
    private static final Pattern POINT_SPLIT = Pattern.compile("\\s*,\\s*");

    /**
     * @param value The {@link String} property value to convert.
     * @return A {@link Point} represented by the x and y coordinate of the input.
     * @throws NullPointerException If the value is null.
     * @throws IllegalArgumentException If the configuration value is an invalid representation of a {@link Point}.
     */
    @Override
    public Point convert(String value) {
        Objects.requireNonNull(value, "Value must not be null.");

        if (value.isEmpty())
            throw new IllegalArgumentException("A point can not be empty.");

        final int lastCharIndex = value.length() - 1;

        if (value.charAt(0) != '(' || value.charAt(lastCharIndex) != ')')
            throw new IllegalArgumentException("Point coordinates must be enclosed in brackets.");

        final String coordinates = value.substring(1, lastCharIndex);
        final String[] xy = POINT_SPLIT.split(coordinates);

        if (xy.length != 2)
            throw new IllegalArgumentException("Point must have an x coordinate, and y coordinate only, expecting the following format: (40, 200)");

        final int x = Integer.parseInt(xy[0]);
        final int y = Integer.parseInt(xy[1]);
        return new Point(x, y);
    }
}
