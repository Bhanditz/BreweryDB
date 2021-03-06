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
import com.txusballesteros.brewerydb.data.search.datasource.SearchQueryLocalDataSource
import com.txusballesteros.brewerydb.data.strategy.CloudStrategy
import javax.inject.Inject

class GetNextPageBeersStrategy private constructor(private val queryLocalDataSource: SearchQueryLocalDataSource,
                                                   private val localDataSource: BeersLocalDataSource,
                                                   private val cloudDataSource: BeersCloudDataSource):
                                CloudStrategy<Void, List<BeerDataModel>>() {

  override fun onRequestCallToCloud(params: Void?): List<BeerDataModel>? {
    val currentPage = localDataSource.getCurrentPage().inc()
    val query = queryLocalDataSource.get()
    val response = cloudDataSource.getList(query, currentPage)
    localDataSource.store(response, currentPage)
    return response
  }

  class Builder @Inject constructor(private val queryLocalDataSource: SearchQueryLocalDataSource,
                                    private val localDataSource: BeersLocalDataSource,
                                    private val cloudDataSource: BeersCloudDataSource) {
    fun build() = GetNextPageBeersStrategy(queryLocalDataSource, localDataSource, cloudDataSource)
  }
}