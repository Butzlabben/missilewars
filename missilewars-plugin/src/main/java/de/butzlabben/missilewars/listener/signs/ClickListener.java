/*
 * This file is part of MissileWars (https://github.com/Butzlabben/missilewars).
 * Copyright (c) 2018-2021 Daniel Nägele.
 *
 * MissileWars is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MissileWars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MissileWars.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.butzlabben.missilewars.listener.signs;

import de.butzlabben.missilewars.MissileWars;
import de.butzlabben.missilewars.game.Game;
import de.butzlabben.missilewars.game.GameManager;
import de.butzlabben.missilewars.util.version.VersionUtil;
import de.butzlabben.missilewars.wrapper.signs.MWSign;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@RequiredArgsConstructor
public class ClickListener implements Listener {

    @EventHandler
    public void onJoinClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (VersionUtil.isWallSignMaterial(block.getType())) {
                Location location = block.getLocation();
                Optional<MWSign> optional = MissileWars.getInstance().getSignRepository().getSign(location);
                if (!optional.isPresent())
                    return;
                MWSign sign = optional.get();
                Game game = GameManager.getInstance().getGame(sign.getLobby());
                if (game == null) return;
                event.getPlayer().teleport(game.getLobby().getSpawnPoint());
            }
        }
    }
}
