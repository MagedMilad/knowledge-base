# Validations

- #### `validates_absence_of` and `validates_presence_of`
```ruby
    class Account < ActiveRecord::Base
        validates_absence_of :something_unwanted
        validates_presence_of :something_important
    end
```
- they are just run `blank?` and `present?`
---
- #### `validates_acceptance_of`
```ruby
    class Account < ActiveRecord::Base
        validates_acceptance_of :privacy_policy, :terms_of_service
        validates_acceptance_of :account_cancellation, accept: 'YES'
    end
```
- you can use this validation with or without a boolean columns on the table backing your model.
- the `accpet` option to change the value that consider accepted the default is `1`
---
- #### `validates_confirmation_of`
```ruby
class Account < ActiveRecord::Base
    validates_confirmation_of :password
end
```
- This validation will create a virtual attribute for the confirmation value and compare the two attributes to make sure they match in order for the model to be valid.
- The user interface used to set values for the Account model would need to include extra text fields named
with a `_confirmation` suffix so for the above example the virtual attricute is `password_confirmation`
---
- #### `validates_format_of`
```ruby
class Person < ActiveRecord::Base
    validates_format_of :email, with: /\A([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})\z/
end
```
---
- #### `validates_inclusion_of` and `validates_exclusion_of`
```ruby
class Person < ActiveRecord::Base
    validates_inclusion_of :gender, in: %w( m f ), message: 'O RLY?'

    validates_exclusion_of :username, in: %w( admin superuser ), message: 'Borat says "Naughty, naughty!"'
end
```
---
- #### `validates_length_of`
```ruby
class Account < ActiveRecord::Base
    validates_length_of :login, minimum: 5
end
```
- `:minimum`, `maximum` option and works as expected
- `within` option for range length `validates_length_of :username, within: 5..20`
- `:is` to match an exact length
- for custome error message you can use `%{count}`
    - `:too_long`
    - `:too_short`
    - `:wrong_length`
        - `wrong_length: "should be %{count} characters long"`
---
- #### `validates_numericality_of`
```ruby
class Account < ActiveRecord::Base
    validates_numericality_of :account_number, only_integer: true
end
```
- used to ensure that an attribute can
only hold a numeric value.
- `only_integer` option for integer values only
- `:equal_to`
- `:greater_than`
- `:greater_than_or_equal_to`
- `:less_than`
- `:less_than_or_equal_to`
- `:other_than`
---
- #### `validates_uniqueness_of`
```ruby
class Address < ActiveRecord::Base
    validates_uniqueness_of :line_two, scope: [:line_one, :city, :zip]
end
```
- This validation does not work by adding a uniqueness constraint at the database level. It does work by constructing and executing a query looking for a matching record in the database
- #### Enforcing Uniqueness of Join Models
 
```ruby
class Student < ActiveRecord::Base
    has_many :registrations
    has_many :courses, through: :registrations
end
class Registration < ActiveRecord::Base
    belongs_to :student
    belongs_to :course
end

class Course < ActiveRecord::Base
    has_many :registrations
    has_many :students, through: :registrations
end
```
- How do you make sure that a student is not registered more than once for a particular course ?
```ruby
class Registration < ActiveRecord::Base
    belongs_to :student
    belongs_to :course

    validates_uniqueness_of :student_id, scope: :course_id,
    message: "can only register once per course"
end
```
- #### Limit Constraint Lookup
- To illustrate, let’s assume we have an `article` that requires titles to be unique against all published articles in the database. We can achieve this using `validates_uniqueness_of` by doing the following:
```ruby
class Article < ActiveRecord::Base
    validates_uniqueness_of :title,
    conditions: -> { where.not(published_at: nil) }
end
```
---
# Common Validation Options

- #### `:allow_blank` and `:allow_nil`
- In some cases, you only want to trigger a validation if a value is present, in other words the attribute is
optional. There are two options that provide this functionality.
---
- #### `:if` and `:unless`
---
- #### `:message`
- All of the validation methods accept a :message option so that you can
override the default error message format
- The default English locale file in ActiveModel defines most of the standard error message templates.
```js
inclusion: "is not included in the list"
exclusion: "is reserved"
invalid: "is invalid"
confirmation: "doesn't match %{attribute}"
accepted: "must be accepted"
empty: "can't be empty"
blank: "can't be blank"
present: "must be blank"
too_long: "is too long (maximum is %{count} characters)"
too_short: "is too short (minimum is %{count} characters)"
wrong_length: "is the wrong length (should be %{count} characters)"
not_a_number: "is not a number"
not_an_integer: "must be an integer"
greater_than: "must be greater than %{count}"
greater_than_or_equal_to: "must be greater than or equal to %{count}"
equal_to: "must be equal to %{count}"
less_than: "must be less than %{count}"
less_than_or_equal_to: "must be less than or equal to %{count}"
other_than: "must be other than %{count}"
odd: "must be odd"
even: "must be even"
```
---
- #### `:on`
- By default, validations are run on save (both create and update operations). If you need to do so, you can limit
a given validation to just one of those operations by passing the `:on` option either `:create` or `:update` .
---
- #### `:strict`
- New to Rails 4 is the :strict validation option. Setting :strict to true causes an exception `ActiveModel::StrictValidation`
to be raised when an model is invalid.
---
# Short-form Validation

Introduced in Rails 3, the `validates` method identifies an attribute and accepts options that correspond to the
validators we’ve already covered in the chapter. Using `validates` can tighten up your model code nicely.
```ruby
validates :username, presence: true,
    format: { with: /[A-Za-z0-9]+/ },
    length: { minimum: 3 },
    uniqueness: true
```
- `absence: true` Alias for `validates_absence_of`
- `acceptance: true` Alias for `validates_acceptance_of`
- `confirmation: true` Alias for `validates_confirmation_of`
- `exclusion: { in: [1,2,3] }` Alias for `validates_exclusion_of`
- `format: { with: /.*/ }` Alias for `validates_format_of`
- `inclusion: { in: [1,2,3] }` Alias for `validates_inclusion_of`
- `length: { minimum: 0, maximum: 1000 }` Alias for `validates_length_of`
- `numericality: true` Alias for `validates_numericality_of`
- `presence: true` Alias for `validates_presence_of`
- `uniqueness: true` Alias for `validates_uniqueness_of`
---
