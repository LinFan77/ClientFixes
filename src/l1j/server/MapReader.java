/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package l1j.server;

import java.io.IOException;
import java.util.Map;

import l1j.server.server.model.map.L1Map;

public abstract class MapReader {
	/**
	 * read all text maps (abstract class)
	 *
	 * @return Map
	 * @throws IOException
	 */
	public abstract Map<Integer, L1Map> read() throws IOException;

	/**
	 * Reads the text map of the specified map number.
	 *
	 * @param id
	 * @return L1Map
	 * @throws IOException
	 */
	public abstract L1Map read(int id) throws IOException;

	/**
	 * Determines the map file to read (text map, cache map, or V2 text map).
	 *
	 * @return MapReader
	 */
	public static MapReader getDefaultReader() {
		if (Config.LOAD_V2_MAP_FILES) {
			System.out.print("V2MapReader loaded...");
			return new V2MapReader();
		}
		if (Config.CACHE_MAP_FILES) {
			System.out.print("CachedMapReader loaded...");
			return new CachedMapReader();
		}
		return new TextMapReader();
	}
}
