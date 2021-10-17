package net.moonlar.ambience.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

  @EventHandler
  public void onWeatherChange(WeatherChangeEvent e) {
    e.setCancelled(true);
  }
}
