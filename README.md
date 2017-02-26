# aaw

Adjective-adjective-animal from Clojure.

Credits for animal and adjective lists go to:
- https://github.com/a-type/adjective-adjective-animal/

## Usage

- Add this library as a dependency to your project.
- Require it in your name space.
- Generate:

```clj
    (require '[ether/aaa :as aaa])
    
    (aaa/generate)
    
    => "contortioned-festive-arrowana"
    
    (aaa/generate :sep "#") 
    
    => "decasyllabic#chartreuse#bongo"

    (generate :path [:adjective :animal :animal :adjective])
    
    => "exportable-indianspinyloach-mealworm-selfindulgent"
```

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
