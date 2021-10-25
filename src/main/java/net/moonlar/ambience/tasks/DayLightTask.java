package net.moonlar.ambience.tasks;

import net.moonlar.ambience.MoonlarAmbience;
import net.moonlar.ambience.helpers.DayLightCycleHelper;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitTask;

import java.util.Calendar;

public class DayLightTask implements Runnable {

  public static final short DAY_TICKS = 24000;
  public static final short MIDNIGHT = 18000;;

  private final MoonlarAmbience plugin;
  private final Server server;

  private BukkitTask task;

  private int ticks;

  public DayLightTask(MoonlarAmbience plugin) {
    this.plugin = plugin;
    this.server = plugin.getServer();
  }

  @Override
  public void run() {
    ++ticks;

    if(ticks >= DAY_TICKS) {
      ticks = 0;
    }

    server.getWorlds().forEach(w -> w.setTime(ticks));
  }

  public void reset() {
    end();

    Calendar calendar = Calendar.getInstance(plugin.getDayLightTimeZone());

    int seconds = calendar.get(Calendar.HOUR_OF_DAY) * 3600;
    seconds += calendar.get(Calendar.MINUTE) * 60;
    seconds += calendar.get(Calendar.SECOND);

    ticks = MIDNIGHT + (int) (seconds / 3.6) - DAY_TICKS;

    server.getWorlds().forEach(world -> DayLightCycleHelper.setRule(world, false));
    task = server.getScheduler().runTaskTimer(plugin, this, 0L, 72);
  }

  public void end() {
    if(task != null) {
      task.cancel();
    }

    task = null;
    ticks = MIDNIGHT;
  }
}
