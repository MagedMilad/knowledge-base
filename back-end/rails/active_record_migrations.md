# Active Record Migrations

Rails provides a generator for creating migrations.
```ruby
$ rails generate migration
Usage:
rails generate migration NAME [field[:type][:index] field[:type][:index]] [options]
```

At minimum, you need to supply descriptive name for the migration in CamelCase (or underscored_text, both
work

## Sequencing Migrations

Migration keep a timestamp 

Migrations that have already been run are listed in a special database table that Rails maintains. It is named
schema_migrations and only has one column:

```sql
mysql> desc schema_migrations;
+---------+--------------+------+-----+---------+-------+
| Field   | Type         | Null | Key | Default | Extra |
+---------+--------------+------+-----+---------+-------+
| version | varchar(255) | NO   | PRI | NULL    |       |
+---------+--------------+------+-----+---------+-------+
1 row in set (0.00 sec)
```

When you pull down new migrations from source control, `rake db:migrate` will check the `schema_migrations` table and execute all migrations that have not yet run (even if they have earlier timestamps
than migrations that you’ve added yourself in the interim).

## change

If your migration is simple you can simply create change function descriping only the up logic
```ruby
class CreateClients < ActiveRecord::Migration
    def change
        create_table :clients do |t|
            t.string :name
            t.string :code
            t.timestamps
        end
    end
end
``` 

## reversible

If a migration is very complex, Active Record may not be able to reverse it without a little help. The `reversible`
method acts very similarly to to the up and down migration methods

```ruby
def change
    reversible do |dir|
        dir.up do
            execute("CREATE EXTENSION hstore")
        end
        dir.down do
            execute("DROP EXTENSION hstore")
        end
    end
    add_column :users, :preferences, :hstore
end
```

## Irreversible Migrations 

used for destructive migrations, when you don't want down migration to happen it should raise `ActiveRecord::IrreversibleMigration` in their down migration

```ruby
def change
    reversible do |dir|
      dir.up do
        # Phone number fields are not integers, duh!
        change_column :clients, :phone, :string
      end
        dir.down { raise ActiveRecord::IrreversibleMigration }
      end
    end
```

## `create_table(name, options, &block)`

The `create_table` method needs at minimum a name for the table and a block containing column definitions.

```ruby
# create table without default auto-generated id column
create_table :ingredients_recipes, id: false do |t|
    t.column :ingredient_id, :integer
    t.column :recipe_id, :integer
end
# change default id column name
create_table :clients, id: :clients_id do |t|
    t.column :name, :string
    t.column :code, :string
    t.column :created_at, :datetime
    t.column :updated_at, :datetime
end
```

## `create_join_table`

The `create_join_table` accepts at minimum the names of two tables.
```ruby
create_join_table :ingredients, :recipes
```
The preceding code example will create a table named `ingredients_recipes` with no primary key.

The `create_join_table` also accepts an options hash
[`:table_name`, `:column_options`, `:options` , `:temporary` , `:force`]

## API Reference `create_table` and `change_table` Block

```ruby
# change
# ------

# change column definition according to new options
t.change :name, :string, limit: 80
t.change :description, :text

# change_default
# --------------
t.change_default :qualification, 'new'
t.change_default :authorized, 1

# column
# ------
# add new column to table
t.column :name, :string

# index
# ------

# simple index
t.index :name

# unique composite index 
t.index [:branch_id, :party_id], unique: true

# unique composite index with index name
t.index [:branch_id, :party_id], unique: true, name: 'by_branch_party'

# belongs_to(args) and references(args)
# -------------------------------------
# These two methods are aliases to each other.
# They add a foreign key column to another model
# adds a _type column if the :polymorphic option is set to true.
create_table :accounts do
    t.belongs_to(:person)
end
create_table :comments do
    t.references(:commentable, polymorphic: true)
end

# :index option to the references and belongs_to methods
# creates an index for the column immediately after creation. 
# The index option accepts a boolean value or the same hash options as the index method
create_table :accounts do
    t.belongs_to(:person, index: true)
end

# remove
# ------

# remove column
t.remove :qualification

# remove_index
# ------------

# remove the accounts_branch_id_index from the accounts table
t.remove_index column: :branch_id

# remove the accounts_branch_id_party_id_index from the accounts table
t.remove_index column: [:branch_id, :party_id]

# remove the index named by_branch_party in the accounts table
t.remove_index name: :by_branch_party

# remove_references(*args) and remove_belongs_to
# ----------------------------------------------
t.remove_belongs_to :person
t.remove_references :commentable, polymorphic: true

# rename(column_name, new_column_name)
# ------------------------------------
t.rename :description, :name

# add_column(table_name, column_definition)
# ----------
# column_definition like column method in create_table block
add_column :clients, :code, :string
add_column :clients, :created_at, :datetime

# Column Options
# --------------
default: value
limit: size
null: false

# Decimal Precision
# ----------------
# Columns declared as type `:decimal` accept the following options:
precision: number
scale: number

# Column Types
# ------------
:binary # an cause large performance problems.
:boolean
:datetime
:timestamp
:time # It’s very, very rare
:decimal # very old use float
:float
:integer
:string
:text
```

## `schema.rb`

The file db/schema.rb is generated every time you migrate and reflects the latest status of your database
schema.

You should never edit db/schema.rb by hand. Since this file is auto-generated from the current state
of the database

Note that this schema.rb definition is the authoritative source for your database schema. If you need to
create the application database on another system, you should be using `db:schema:load`

## Database-Related Rake Tasks


### `db:create` and `db:create:all`

Create the database defined in `config/database.yml` for the current `Rails.env` . If the current environment
is development, Rails will create both the local development and test databases.(Or create all of the local
databases defined in `config/database.yml` in the case of `db:create:all` .)

---

### `db:drop` and `db:drop:all`
Drops the database for the current `RAILS_ENV` . If the current environment is development, Rails will drop both
the local development and test databases. (Or drops all of the local databases defined in `config/database.yml`
in the case of `db:drop:all` .)

---

### `db:forward` and `db:rollback`
The `db:rollback` task moves your database schema back one version. Similarly, the `db:forward` task moves
your database schema forward one version and is typically used after rolling back.

---

### `db:migrate`

Applies all pending migrations. If a `VERSION` environment variable is provided, then `db:migrate` will apply
pending migrations through the migration specified, but no further. The `VERSION` is specified as the timestamp
portion of the migration file name.

`$ rake db:migrate VERSION=20130313005347 `

---

### `db:migrate:down` and `db:migrate:up`
This task will invoke the `down` or `up` method of the specified migration only. The `VERSION` is specified as the
timestamp portion of the migration file name.

---

### `db:migrate:reset`
Resets your database for the current environment using your migrations (as opposed to using `schema.rb` ).

---

### `db:migrate:status`
Displays the status of all existing migrations in a nicely formatted table

---

### `db:reset` and `db:setup`
The `db:setup` creates the database for the current environment, loads the schema from `db/schema.rb` , then
loads the seed data. It’s used when you’re setting up an existing project for the first time on a development
workstation. The similar `db:reset` task does the same thing except that it drops and recreates the database
first.

---

### `db:seed`
Load the seed data from `db/seeds.rb` as described in this chapter’s section Database Seeding.

---

### `db:structure:dump`
Dump the database structure to a SQL file containing raw DDL code in a format corresponding to the database
driver specified in `database.yml` for your current environment.

---

### `db:version`
Returns the timestamp of the latest migration file that has been run. Works even if your database has been
created from `db/schema.rb` , since it contains the latest version timestamp in it


