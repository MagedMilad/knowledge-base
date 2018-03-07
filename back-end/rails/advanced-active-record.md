# Advanced Active Record

# Scopes
```ruby
class User < ActiveRecord::Base
    scope :delinquent, -> { where('timesheets_updated_at < ?', 1.week.ago) }

    # scope paramters
    scope :newer_than, ->(date) { where('start_date > ?', date) }

    # or
    def self.delinquent
        where('timesheets_updated_at < ?', 1.week.ago)
    end

    # scopes with joins
    scope :tardy, -> {
        joins(:timesheets).
        where("timesheets.submitted_at <= ?", 7.days.ago).
        group("users.id")
    }

    # this is the same as the above but we moved timesheets where condition to a seperate scope called late and used merge methods to merge scopes span classes
    scope :tardy, -> {
        joins(:timesheets).
        group("users.id").
        merge(Timesheet.late)
    }

end

>> User.delinquent
# => [#<User id: 2, timesheets_updated_at: "2013-04-20 20:02:13"...>]

>> User.tardy.to_sql
# => "SELECT "users".* FROM "users"
# INNER JOIN "timesheets" ON "timesheets"."user_id" = "users"."id"
# WHERE (timesheets.submitted_at <= '2013-04-13 18:16:15.203293')
# GROUP BY users.id" # query formatted nicely for the book
```

- scopes are also avaiable in has_many association attributes

## Default Scopes

```ruby
class Timesheet < ActiveRecord::Base
    default_scope { where(status: "open") }
end

>> Timesheet.pluck(:status)
=> ["open", "open", "open"]

>> Timesheet.new
=> #<Timesheet id: nil, status: "open">
>> Timesheet.create
=> #<Timesheet id: 1, status: "open">

# we can override this behaviour by providing our own condition or scope
>> Timesheet.where(status: "new").new
=> #<Timesheet id: nil, status: "new">
>> Timesheet.where(status: "new").create
=> #<Timesheet id: 1, status: "new">

# we can override this behaviour also by using unscoped 
>> Timesheet.unscoped.new
=> #<Timesheet id: nil, status: nil>
```