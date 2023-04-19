/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.command.executor;

import l1j.server.server.model.Instance.L1PcInstance;

/**
 * Command execution processing interface
 *
 * Command processing class other than this interface method
 * public static L1CommandExecutor getInstance()
 * must be implemented. Normally, subclasses are instantiated and returned, but cached
 * instances are returned if necessary, or other classes are instantiated.
 * Can be returned.
 */
public interface L1CommandExecutor {
	/**
	 * Run this command
	 *
	 * @param pc
	 * @param cmdName
	 * @param arg
	 */
	public void execute(L1PcInstance pc, String cmdName, String arg);
}