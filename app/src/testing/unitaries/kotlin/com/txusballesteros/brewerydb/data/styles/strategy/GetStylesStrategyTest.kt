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
package com.txusballesteros.brewerydb.data.styles.strategy

import com.nhaarman.mockito_kotlin.whenever
import com.txusballesteros.brewerydb.UnitTest
import com.txusballesteros.brewerydb.data.model.StyleDataModel
import com.txusballesteros.brewerydb.data.styles.datasource.StylesCloudDataSource
import com.txusballesteros.brewerydb.data.styles.datasource.StylesLocalDataSource
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*

class GetStylesStrategyTest : UnitTest() {
  companion object {
    private val CATEGORY_ID: Int = 1
    private val STYLE_ID : Int = 1
    private val STYLE_NAME : String = "Classic English-Style Pale Ale"
    private val STYLE_SHORT_NAME : String = "English Pale"
    private val STYLE_DESCRIPTION : String = "Classic English pale ales are golden to copper colored and display earthy,..."
  }

  lateinit var localDataSource: StylesLocalDataSource
  lateinit var cloudDataSource: StylesCloudDataSource
  lateinit var stylesList: ArrayList<StyleDataModel>
  lateinit var strategy: GetStylesStrategy

  override fun onPrepareTest() {
    stylesList = ArrayList<StyleDataModel>()
    stylesList.add(StyleDataModel(STYLE_ID, CATEGORY_ID, STYLE_NAME, STYLE_SHORT_NAME, STYLE_DESCRIPTION))
    localDataSource = Mockito.mock(StylesLocalDataSource::class.java)
    cloudDataSource = Mockito.mock(StylesCloudDataSource::class.java)
    strategy = GetStylesStrategy.Builder(localDataSource, cloudDataSource).build()
  }

  @Test
  fun shouldGetStylesFromCloud() {
    whenever(localDataSource.getList())
        .thenReturn(null)
        .thenReturn(stylesList)
    whenever(cloudDataSource.getList()).thenReturn(stylesList)

    strategy.execute(onResult =  {
      Assert.assertNotNull(it)
      Assert.assertEquals(stylesList.size, it?.size)
      Assert.assertEquals(STYLE_ID, it?.first()?.id)
      verify(cloudDataSource).getList()
    })
  }

  @Test
  fun shouldGetStylesFromLocal() {
    whenever(localDataSource.getList()).thenReturn(stylesList)
    whenever(cloudDataSource.getList()).thenReturn(null)

    strategy.execute(onResult =  {
      Assert.assertNotNull(it)
      Assert.assertEquals(stylesList.size, it?.size)
      Assert.assertEquals(STYLE_ID, it?.first()?.id)
      verify(cloudDataSource, never()).getList()
    })
  }
}