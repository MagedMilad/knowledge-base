# Routing in Rails

## The Two Purposes of Routing
the routing system in rails does two things

1. It maps requests to controller action methods
1. it enables the dynamic generation of URLs for you for use as arguments to methods like `link_to` and `redirect_to`

## Regular Routes

```ruby
get 'products/:id' => 'products#show'
```

it’s recommended to limit the HTTP method used to access a route.

```ruby
match 'products/:id' => 'products#show', via: :get
```

### shorthand way

```ruby
get 'products/:id' => 'products#show'
post 'products' => 'products#create'
```

If, for some reason, you want to constrain a route to more than one HTTP method, you can pass :via an array
of verb names.

```ruby
match 'products/:id' => 'products#show', via: [:get, :post]
```

Defining a route without specifying an HTTP method will result in Rails raising a RuntimeError exception.
While strongly not recommended, a route can still match any HTTP method by passing `:any` to the `:via` option.

```ruby
match 'products' => 'products#index', via: :any
```

## Redirect Routes

It’s possible to code a redirect directly into a route definition, using the redirect method:

```ruby
get "/foo", to: redirect('/bar')
```

```ruby
get "/google", to: redirect('https://google.com/')
```

The redirect method can also take a block, which receives the request params as its argument. This allows
you to, for instance, do quick versioning of web service API endpoints

```ruby
match "/api/v1/:api",
to: redirect { |params| "/api/v2/#{params[:api].pluralize}" ,via: :any
```

redirect can take status code

```ruby
match "/api/v1/:api", to:
redirect(status: 302) { |params| "/api/v2/#{params[:api].pluralize}" }, via: :any
```

## The Format Segment

the legacy default route
```ruby
match ':controller(/:action(/:id(.:format)))', via: :any
```

Here, `params[:format]` will be set to json . The :format field is special; it has an effect inside the controller
action. That effect is related to a method called `respond_to` .

The `respond_to` method allows you to write your action so that it will return different results, depending on
the requested format. Here’s a show action for the products controller that offers either HTML or JSON:

```ruby
def show
    @product = Product.find(params[:id])
    respond_to do |format|
        format.html
        format.json { render json: @product.to_json }
    end
end
```

Requesting a format that is not included as an option in the respond_to block will not generate an exception.
Rails will return a 406 Not Acceptable status, to indicate that it can’t handle the request.
If you want to setup an else condition for your `respond_to` block, you can use the any method, which tells
Rails to catch any other formats not explicitly defined.

```ruby
def show
    @product = Product.find(params[:id])
    respond_to do |format|
        format.html
        format.json { render json: @product.to_json }
        format.any
    end
end
```

## Accept Header
You can also trigger a branching on respond_to by setting the Accept header in the request. When you do this,
there’s no need to add the `.:format` part of the URL

## Segment Key Constraints
Sometimes you want not only to recognize a route, but to recognize it at a finer-grained level than just what
components or fields it has. You can do this through the use of the :constraint option (and possibly regular
expressions).

For example, you could route all show requests so that they went to an error action if their id fields were
non-numerical. You’d do this by creating two routes, one that handled numerical ids, and a fall-through route
that handled the rest:

```ruby
get ':controller/show/:id' => :show, id: /\d+/
get ':controller/show/:id' => :show_error
```

## The Root Route

```ruby
root "welcome#index"
```

## Route Globbing

In some situations, you might want to grab one or more components of a route without having to match them
one by one to specific positional parameters. For example, your URLs might reflect a directory structure. If
someone connects to

`/items/list/base/books/fiction/dickens`

you want the items/list action to have access to all four remaining fields. But sometimes there might be
only three fields:

`/items/list/base/books/fiction`

or five:

`/items/list/base/books/fiction/dickens/little_dorrit`

```ruby
get 'items/list/*specs', controller: 'items', action: 'list'
```
Now, the `items/list` action will have access to a variable number of slash-delimited URL fields, accessible
via `params[:specs]` :

```ruby
def list
    specs = params[:specs] # e.g, "base/books/fiction/dickens"
end
```

## Named Routes
When you name a route, a new method gets defined for use in your controllers and views; the method is called `name_url` and `name_url`

```ruby
get 'help' => 'help#index', as: 'help'
```

so now you have `help_path` and `help_url`

this is another example using argument named routes
```ruby
get "item/:id" => "items#show", as: "item"
```
```ruby
link_to "Auction of #{item.name}", item_path(item)
```

## Scoping Routing Rules

```ruby
scope path: '/auctions', controller: :auctions do
    get 'new' => :new
    get 'edit/:id' => :edit
    post 'pause/:id' => :pause
end
```

New to Rails 4, is the ability to pass the :path option symbols instead of strings. The following scope definition:

```ruby
scope :auctions, :archived do
```

## Name Prefix
```ruby
scope :auctions, as: 'admin' do
    get 'new' => :new, as: 'new_auction'
end
```

will generate a named route URL helper method called `admin_new_auction_url`


## Namespaces
URLs can be grouped by using the namespace method, which is syntactic sugar that rolls up module, name
prefix and path prefix settings into one declaration. The implementation of the namespace method converts
its first argument into a string, which is why in some example code you’ll see it take a symbol.

```ruby
namespace :auctions, :controller => :auctions do
    get 'new' => :new
    get 'edit/:id' => :edit
    post 'pause/:id' => :pause
end
```

## Bundling Constraints
If you find yourself repeating similar segment key constraints in related routes, you can bundle them together
using the :constraints option of the scope method:

```ruby
scope controller: :auctions, constraints: {:id => /\d+/} do
    get 'edit/:id' => :edit
    post 'pause/:id' => :pause
end
```

```ruby
scope path: '/auctions', controller: :auctions do
    get 'new' => :new
    constraints id: /\d+/ do
        get 'edit/:id' => :edit
        post 'pause/:id' => :pause
    end
end
```

## Listing Routes
`$ rake routes`
