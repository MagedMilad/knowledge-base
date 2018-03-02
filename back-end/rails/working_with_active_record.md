# Working with Active Record

Active Record class named `Client` will be mapped to the `clients` table

```ruby
class Client < ActiveRecord::Base
    self.table_name = "CLIENT"
    self.primary_key = "CLIENT_ID"
end
```

## overriding default accessor in ActiveRecord
first name the method with the same name as the arrtibute
```ruby
# orriding default read accessor
class Specification < ActiveRecord::Base
    def tolerance
        self[:tolerance] || 'n/a'
    end
end
# orriding default write accessor
class SillyFortuneCookie < ActiveRecord::Base
    def message=(txt)
        self[:message] = txt + ' in bed'
    end
end
```

## ActiveRecord::Store
Rails introduced the store declaration, which uses serialize behind the scenes to declare
a single-column key/value store. (store a hash)
```ruby
class User < ActiveRecord::Base
    store :preferences
end
```

## Creating New Active Record Instances

```ruby
c = Client.new
#<Client id: nil, name: nil, code: nil>
c.new_record?
# true
c.persisted?
# false
```
we can use `create` or `new` function to create new ActiveRecord instance and both can accept block of initialization

`create` save the recrod in the database

`new` don't save 'persist' the record until you do so

## The Query Cache
By default, Rails attempts to optimize performance by turning on a simple query cache. It is a hash stored on
the current thread, one for every active database connection

You can enable the query cache manually by wrapping operations in a cache block

```ruby
User.cache do
    puts User.first
    puts User.first
    puts User.first
end
```

## Touching Records
There may be certain cases where updating a time field to indicate a record was viewed is all you require,
and Active Record provides a convenience method for doing so in the form of `touch`
```ruby
>> user = User.first
>> user.touch # => sets updated_at to now.
>> user.touch(:viewed_at) # sets viewed_at and updated_at to now.


class User < ActiveRecord::Base
belongs_to :client, touch: true
end
>> user.touch # => also calls user.client.touch
```

## Readonly Attributes
```ruby
class Customer < ActiveRecord::Base
    attr_readonly :social_security_number
end

>> customer = Customer.new(social_security_number: "130803020")
#<Customer id: 1, social_security_number: "130803020", ...>
>> customer.social_security_number
# "130803020"
>> customer.save
>> customer.social_security_number = "000000000"
>> customer.social_security_number
# "000000000"

# Note, no error raised!
customer.save
customer.reload
customer.social_security_number
"130803020" # the original readonly value is preserved
```

## Database Locking
### Optimistic Locking
Optimistic locking describes the strategy of detecting and resolving collisions if they occur, and is commonly
recommended in multi-user situations where collisions should be infrequent.

#### Implementation
If you control your database schema, optimistic locking is really simple to implement. Just add an integer
column named lock_version to a given table, with a default value of zero.
```ruby
class AddLockVersionToTimesheets < ActiveRecord::Migration
    def change
        add_column :timesheets, :lock_version, :integer, default: 0
    end
end
```
Simply adding that lock_version column changes Active Record’s behavior. Now if the same record is loaded
as two different model instances and saved differently, the first instance will win the update, and the second
one will cause an `ActiveRecord::StaleObjectError` to be raised.

### Pessimistic Locking

```ruby
Timesheet.transaction do
    t = Timesheet.lock.first
    t.approved = true
    t.save!
end
```

## ActiveRecord Query

```ruby
# where
# where accept hash or string
Product.where(sku: params[:sku])
# hash form is intellegent to understand in clause
Product.where(sku: [9400,9500,9900])
Product.where('sku in (?)', selected_skus)
Article.where.not(title: 'Rails 3')
Article.where.not(title: ['Rails 3', 'Rails 5'])
# binding variables
Product.where("name = :name AND sku = :sku AND created_at > :date", name: "Space Toilet", sku: 80800, date: '2009-01-01')
# boolean
Timesheet.where('submitted = ?', true)
# nil condition
User.where(email: nil)
# order
Timesheet.order('created_at desc')
Timesheet.order(:created_at)
# limit & offset
Timesheet.limit(10).offset(10)
# select
BillableWeek.select("mon_hrs + tues_hrs as two_day_total").first
# full sql query
select("#{table_name}.*").
    from("#{table_name}, tags, taggings").
    where("#{table_name}.#{primary_key} = taggings.taggable_id
    and taggings.tag_id = tags.id
    and tags.name IN (?)",
    Tag.parse(list))
# exists?
# use ids, hash/string clause, or like ruby exists?
User.exists?(1)
User.exists?(login: "mack")
User.exists?(id: [1, 3, 5])
User.where(login: "mack").exists?
# extending
# Specifies one or many modules with methods that will extend the scope with additional methods.
module Pagination
    def page(number)
        # pagination code
    end
end
scope = Model.all.extending(Pagination)
scope.page(params[:page])
# group
Account.select('name, SUM(cash) as money').group('name').to_a
# having
User.group("created_at").having(["created_at > ?", 2.days.ago])
# joins
Buyer.select('buyers.id, count(carts.id) as cart_count').
    joins('left join carts on carts.buyer_id = buyers.id').
    group('buyers.id')
# uniq / distinct
User.select(:login).uniq
```
