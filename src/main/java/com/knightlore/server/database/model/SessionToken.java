package com.knightlore.server.database.model;

import java.sql.Types;

/**
 * Session Token instance of model
 * @author Lewis Relph
 */
public class SessionToken extends Model {

  public static SessionToken instance = new SessionToken();

  public SessionToken() {
    this.table = "session_tokens";
    this.attributes = new AttributeBag();
    this.attributes.addAttribute("id", new Attribute(Types.INTEGER, null, true));
    this.attributes.addAttribute("registration_key_id", new Attribute(Types.INTEGER, null));
    this.attributes.addAttribute("token", new Attribute(Types.VARCHAR, null));
    this.attributes.addAttribute("ip", new Attribute(Types.VARCHAR, null));
    this.attributes.addAttribute("created_at", new Attribute(Types.TIMESTAMP, null));
    this.attributes.addAttribute("last_used_at", new Attribute(Types.TIMESTAMP, null));
    this.attributes.addAttribute("expires_at", new Attribute(Types.TIMESTAMP, null));

    this.identifier = "id";
  }

  public Model createNewInstance() {
    return new SessionToken();
  }

  public RegistrationKey registrationKey() {
    RegistrationKey key = RegistrationKey.instance;
    key.find(this.getAttribute("registration_key_id"));
    return key;
  }
}
