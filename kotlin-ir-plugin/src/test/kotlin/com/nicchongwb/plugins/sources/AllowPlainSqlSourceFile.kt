package com.nicchongwb.plugins.sources

import org.jooq.Allow
import org.jooq.Record
import org.jooq.Result
import org.jooq.SelectConditionStep
import org.jooq.impl.DSL
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.table
import java.sql.DriverManager

class AllowPlainSqlSourceFile {
  val url = "jdbc:h2:~/test;AUTO_SERVER=TRUE"
  val username = "sa"
  val password = ""

  private val dsl = DriverManager.getConnection(url, username, password).use {
    DSL.using(it)
  }

  @Allow.PlainSQL
  fun findAll(): SelectConditionStep<Record> {
    return dsl.select()
      .from(table("test_table"))
      .where(field("test_column").eq("test_value"))
      .and("test_condition_a")
      .and(field("test_condition_b").eq("condition_c"))
  }

  @Allow.PlainSQL
  fun plainSqlFindByStringId(id: String): Result<Record> {
    return dsl.fetch("SELECT * FROM test_table WHERE id=" + id)
  }

  @Allow.PlainSQL
  fun plainSqlUpdateQuery(username: String, oldPass: String, newPass: String): Result<Record> {
    return dsl.fetch("UPDATE test_table SET pass='" + newPass + "' WHERE username='" + username + "' AND password='" + oldPass + "'")
  }
}
