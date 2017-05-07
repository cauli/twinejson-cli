# twinejson-cli

CLI tool for exporting Twine 2 stories into json

### Basic Usage

Publish our archive your story to `html` then:

> node <path_to>/twinejson.js <path_to>/<input_html>.html <path_to>/<output_json>.json
> node <path_to>/twinejson.js ~/Desktop/my_story.html ~/Desktop/my_story.json

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
