package com.knightlore.server.database.model;

import java.sql.Types;

/**
 * Game instance of model
 * @author Lewis Relph
 */
public class Game extends Model {

  public static Game instance = new Game();

  public Game() {
    this.table = "games";
    this.attributes = new AttributeBag();
    this.attributes.addAttribute("id", new Attribute(Types.INTEGER, null, true));
    this.attributes.addAttribute("session_id", new Attribute(Types.INTEGER, null));
    this.attributes.addAttribute("uuid", new Attribute(Types.VARCHAR, null));
    this.attributes.addAttribute("ip", new Attribute(Types.VARCHAR, null));
    this.attributes.addAttribute("port", new Attribute(Types.INTEGER, null));
    this.attributes.addAttribute("created_at", new Attribute(Types.TIMESTAMP, null));
    this.attributes.addAttribute("started_at", new Attribute(Types.TIMESTAMP, null));
    this.attributes.addAttribute("ends_at", new Attribute(Types.TIMESTAMP, null));
    this.attributes.addAttribute("name", new Attribute(Types.VARCHAR, null));

    this.identifier = "id";
  }

  public Model createNewInstance() {
    return new Game();
  }
}
