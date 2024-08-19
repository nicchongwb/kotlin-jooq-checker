package io.github.nicchongwb.ktjooqchecker.sources

import org.jooq.Record
import org.jooq.Result
import org.jooq.SelectConditionStep
import org.jooq.impl.DSL
import java.sql.DriverManager

class PlainSqlSourceFile {
  val url = "jdbc:h2:~/test;AUTO_SERVER=TRUE"
  val username = "sa"
  val password = ""

  private val dsl = DriverManager.getConnection(url, username, password).use {
    DSL.using(it)
  }

  fun findAll(): SelectConditionStep<Record> {
    return dsl.select()
      .from(DSL.table("test_table"))
      .where(DSL.field("test_column").eq("test_value"))
      .and("test_condition_a")
      .and(DSL.field("test_condition_b").eq("condition_c"))
  }

  fun plainSqlFindByStringId(id: String): Result<Record> {
    return dsl.fetch("SELECT * FROM test_table WHERE id=" + id)
  }

  fun plainSqlUpdateQuery(username: String, oldPass: String, newPass: String): Result<Record> {
    return dsl.fetch("UPDATE test_table SET pass='" + newPass + "' WHERE username='" + username + "' AND password='" + oldPass + "'")
  }
}
