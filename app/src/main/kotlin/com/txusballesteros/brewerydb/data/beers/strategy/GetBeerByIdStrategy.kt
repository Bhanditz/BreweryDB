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
package com.txusballesteros.brewerydb.data.beers.strategy

import com.txusballesteros.brewerydb.data.beers.datasource.BeersCloudDataSource
import com.txusballesteros.brewerydb.data.beers.datasource.BeersLocalDataSource
import com.txusballesteros.brewerydb.data.model.BeerDataModel
import com.txusballesteros.brewerydb.data.strategy.LocalOrCloudStrategy
import javax.inject.Inject

class GetBeerByIdStrategy private constructor(private val localDataSource: BeersLocalDataSource,
                                              private val cloudDataSource: BeersCloudDataSource):
                          LocalOrCloudStrategy<String, BeerDataModel>() {

  override fun onRequestCallToLocal(params: String?): BeerDataModel? {
    return localDataSource.get(params!!)
  }

  override fun onRequestCallToCloud(params: String?): BeerDataModel? {
    val result = cloudDataSource.get(params!!)
    localDataSource.store(result)
    return result
  }

  class Builder @Inject constructor(private val localDataSource: BeersLocalDataSource,
                                    private val cloudDataSource: BeersCloudDataSource) {
    fun build() = GetBeerByIdStrategy(localDataSource, cloudDataSource)
  }
}