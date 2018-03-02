# Active Record Associations

## One-to-Many Relationships
```ruby
class User < ActiveRecord::Base
    has_many :timesheets
    has_many :expense_reports
end

class Timesheet < ActiveRecord::Base
    belongs_to :user
end
class ExpenseReport < ActiveRecord::Base
    belongs_to :user
end
# now you need to add a foreign key column in the timesheets
# and exprense_reports table in order to use user.timesheets
class AddUserForeignKeys < ActiveRecord::Migration
    def change
        add_column :timesheets, :user_id, :integer
        add_column :expense_reports, :user_id, :integer
    end
end
# you can add to :has_many collection like
user.timesheets << TimeSheets.new
user.timesheets.create
```
### Association Collection Methods

Association collections have all normal array methods, scopes, and active record methods

```ruby
user.timesheets.where(submitted: true).order('updated_at desc')
```
### Example methods

- `<<(*records)` and `create(attributes = {})`
    -  both methods used to add new object to the collection, and both trigger `:before_add` and `:after_add` callbacks
- `any?` and `many?`
- `average(column_name, options = {})`
- `build(attributes={}, &block)`
    - `build` used like `new` to create new element without saving it into the database but `new` is better
    - we use `build` if the attributes parameter is an array of hashes (instead of just one) then build executes for each one. However, you would usually accomplish that kind of behavior using `accepts_nested_attributes_for`
- `calculate(operation, column_name, options = {})`
    - Provides aggregate ( `:sum` , `:average`, `:minimum` and `:maximum` )
- `clear`
    - similar to `delete_all` however instead of returning an array of deleted objects, it is chainable
- `count(column_name=nil, options={})`
    - count the association array, it `column_name` is set so it will be used insead of `count(*)`
- `delete(*records)` and `delete_all`
    - The names of the `delete` and `delete_all` methods can be misleading. By default, they don’t delete anything from the database—they only sever associations by clearing the foreign key field of the associated record.
    - This behavior is related to the `:dependent` option, which defaults to `:nullify` . If the association is configured with the `:dependent` option set to `:delete` or `:destroy` , then the associated records will actually be deleted from the database.
- `empty?`
- `find(id)`
- `first(*args)`
    - the `args` options to load the first `n`object in the association
- `ids`
    - call `pluck(primary_key)`
- `include?(record)`
- `last(*args)`
- `length`
    - loads the hole association in the memory and call `size` on it
- `maximum(column_name, options = {})`
    - calls `calculate(:maximum)`
- `minimum(column_name, options = {})`
    - calls `calculate(:minimum)`
- `new(attributes, &block)`
- `pluck(*column_names)`
- `replace(other_array)`
    - Replaces the collection with `other_array` . Works by deleting objects that exist in the current collection, but not in `other_array` and inserting (using `concat` ) objects that don’t exist in the current collection, but do exist in `other_array` .
- `select(select=nil, &block)`
- `sum(column_name, options = {})`
---

## The `belongs_to` Association

Defining a `belongs_to` relationship on a class creates a method with the same name on its instances. As
mentioned earlier, the method is actually a proxy to the related Active Record object and adds capabilities
useful for manipulating the relationship.

### Reloading the Association
Just invoking the association method will query the database (if necessary) and return an instance of the
related object. The method takes a `force_reload` parameter that tells Active Record whether to reload the
related object, if it happens to have been cached already by a previous access.

```ruby
ts = Timesheet.first
#<Timesheet id: 3, user_id: 1...>
ts.user.object_id
# 70279541443160
ts.user.object_id
# 70279541443160
ts.user(true).object_id
# 70279549419740
```

### Building and Creating Related Objects via the Association

The `build_association` method does not save the new object, but the `create_association` method does.
Both methods take an optional hash of attribute parameters with which to initialize the newly instantiated
objects

```ruby
>> ts = Timesheet.first
=> #<Timesheet id: 3, user_id: 1...>
>> ts.build_user
=> #<User id: nil, email: nil...>
>> bc = BillingCode.first
=> #<BillingCode id: 1, code: "TRAVEL"...>
>> bc.create_client
=> #<Client id: 1, name=>nil, code=>nil...>
```

### `belongs_to` Options

```ruby
- autosave: true

- :class_name
class Timesheet < ActiveRecord::Base
    belongs_to :approver, class_name: 'User'
    belongs_to :user

- :counter_cache
# Use this option to make Rails automatically update a 
#counter field on the associated object with the number
# of belonging objects. The option value can be true , in 
# which case the pluralized name of the belonging class
# `plus _count` is used, or you can supply your own column name to be used:
counter_cache: true
counter_cache: :number_of_children

- dependent: :destroy or :delete
- foreign_key: :column_name
- inverse_of: :name_of_has_association
- polymorphic: true
create_table :comments do |t|
    t.text :body
    t.references :subject, polymorphic: true
    # references can be used as a shortcut for following two statements
    # t.integer :subject_id
    # t.string :subject_type
    t.timestamps
end
class Comment < ActiveRecord::Base
    belongs_to :subject, polymorphic: true
end
class ExpenseReport < ActiveRecord::Base
    belongs_to :user
    has_many :comments, as: :subject
end

class Timesheet < ActiveRecord::Base
    belongs_to :user
    has_many :comments, as: :subject
end

- primary_key: :column_name # for legacy database only i think
```

### `belongs_to` Scopes

Sometimes the need arises to have a relationship that must satisfy certain conditions in order for it to be valid.

```ruby
- where(*conditions)
class Timesheet < ActiveRecord::Base
    belongs_to :approver, { where(approver: true) },
    class_name: 'User'
end

- includes
belongs_to :post, -> { includes(:author) }
- readonly
```

## The `has_many` Association

### `has_many` Options

```ruby
- after_add: callback or before_add: callback
# Called after a record is added to the collection via the
# << method. Is not triggered by the collection’s create

- after_remove: callback or before_remove: callback
# Called after a record has been removed from the collection with the delete method.

- as: association_name # for using in polymorphic case
- autosave: true
- :class_name
- dependent: :delete_all
#All associated objects are deleted in fell swoop using a 
#single SQL command. Note: While this option is much
#faster than :destroy , it doesn’t trigger any destroy 
#callbacks on the associated objects
- dependent: :destroy
- dependent: :nullify
# the related object remains the same and the foreign keys 
# only set as null

- foreign_key: column_name
- inverse_of: name_of_belongs_to_association
# Explicitly declares the name of the inverse association in a bi-directional relationship.
- primary_key: column_name
- through: association_name
```

