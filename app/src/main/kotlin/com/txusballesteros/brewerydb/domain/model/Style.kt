/*
 * Copyright Txus Ballesteros 2017 (@txusballesteros)
 *
 * This file is part of Foobar.
 *
 * Foobar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact: Txus Ballesteros <txus.ballesteros@gmail.com>
 */
package com.txusballesteros.brewerydb.domain.model

data class Style(val id: Int,
                 val categoryId: Int,
                 val name: String,
                 val shortName: String,
                 val description: String,
                 val ibuMin: String,
                 val ibuMax: String,
                 val abvMin: String,
                 val abvMax: String,
                 val srmMin: String,
                 val srmMax: String,
                 val ogMin: String,
                 val ogMax: String,
                 val fgMin: String,
                 val fgMax: String)