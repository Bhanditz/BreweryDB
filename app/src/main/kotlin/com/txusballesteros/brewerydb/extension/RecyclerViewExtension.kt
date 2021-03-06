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
package com.txusballesteros.brewerydb.extension

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun RecyclerView.setEmptyView(view: View) {
  adapter?.apply {
    val observer = object: RecyclerView.AdapterDataObserver() {
      override fun onChanged() {
        view.visibility = if (isEmpty()) VISIBLE else GONE
        this@setEmptyView.visibility = if (isEmpty()) GONE else VISIBLE
      }
    }
    registerAdapterDataObserver(observer)
    observer.onChanged()
  }
}

fun <A: RecyclerView.ViewHolder> RecyclerView.Adapter<A>.isEmpty() = itemCount == 0
