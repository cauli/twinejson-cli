# twinejson-cli

CLI tool for exporting Twine 2 stories into json


### Basic Usage

Publish our archive your story to `html` then:

```
$ npm install twinejson-cli
twinejson-cli@0.0.2 node_modules/twinejson-cli
└── ws@2.3.1 (ultron@1.1.0, safe-buffer@5.0.1)

$ node node_modules/twinejson-cli/twinejson-min.js ~/path/input.html ~/path/output.json
Successfully written to '~/path/output.json'
```

### Building from Source


`lein cljsbuild once min` will build a minified file at `twinejson-min.js`

or 

`lein figwheel dev` will open a REPL with hot reloading using `figwheel`


### Output Sample

Output:

```
[  
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"1",
         "name":"Start",
         "tags":"tag1 tag-with-many-spaces",
         "position":"391,299.5"
      },
      "content":[  
         "WHAT\n\n[[b]]\n[[d]]\n[[e]]\n[[f->g]]"
      ],
      "links":[  
         "b",
         "d",
         "e",
         "g"
      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"2",
         "name":"b",
         "tags":"",
         "position":"636,330.5"
      },
      "content":[  
         "[[c]]"
      ],
      "links":[  
         "c"
      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"3",
         "name":"c",
         "tags":"",
         "position":"816,197.5"
      },
      "content":[  
         "[[ca]]\n[[cb]]\n[[cd]]\n\n[[c]]"
      ],
      "links":[  
         "ca",
         "cb",
         "cd",
         "c"
      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"4",
         "name":"d",
         "tags":"",
         "position":"605,503.5"
      },
      "content":[  
         "Yada yada blada blada\n\n[[Start]]"
      ],
      "links":[  
         "Start"
      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"5",
         "name":"e",
         "tags":"",
         "position":"530,147.5"
      },
      "content":[  
         "[[e->a->c->ea]]"
      ],
      "links":[  
         "ea"
      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"6",
         "name":"g",
         "tags":"",
         "position":"396,511.5"
      },
      "content":[  
         "[[g->h]]"
      ],
      "links":[  
         "h"
      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"7",
         "name":"h",
         "tags":"",
         "position":"210,507.5"
      },
      "content":[  
         "[[Start]]"
      ],
      "links":[  
         "Start"
      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"8",
         "name":"ca",
         "tags":"",
         "position":"1012,247.5"
      },
      "content":[  
         "Double-click this passage to edit it."
      ],
      "links":[  

      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"9",
         "name":"cb",
         "tags":"",
         "position":"802,450.5"
      },
      "content":[  
         "Double-click this passage to edit it."
      ],
      "links":[  

      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"10",
         "name":"cd",
         "tags":"",
         "position":"952,450.5"
      },
      "content":[  
         "Double-click this passage to edit it."
      ],
      "links":[  

      ]
   },
   {  
      "tag":"tw-passagedata",
      "attributes":{  
         "pid":"11",
         "name":"ea",
         "tags":"",
         "position":"659,31.5"
      },
      "content":[  
         "[[c<-ea]]"
      ],
      "links":[  
         "c"
      ]
   }
]
```
