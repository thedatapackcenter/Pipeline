# Pipeline
The Pipeline Pipedream! This is a project concept for an easy intuitive server addon that increases the potential and power of data packs with only a few tweaks.

# Pipeline Custom Command System
All Pipeline command files are jsons, they should share the same name as the custom command they are defining but it's not required. They are stored in `DATAPACK/data/custom/pipeline/commands/`

- Name stores the string used after the slash `/<command name goes here>`, it can not contain spaces, and is recommeneded to only be made of unicode letters.

- Alias is an optional field that stores an alternative to the name for the command.

- Description stores information about the command found when using /help.

- Argbuilder is an optional field where you line up additional arguments to the command which are parsed into the function later using the argument names -> Syntax here `<argType[]:argName>`.

  Planned argument types:
  * entity - all entities, accepts @s, @p, @r, @a, @e or a player username.
  * pentity - only player entities, accepts @s, @p, @r, @a or a player username *@s may be limited in this argument.
  * npentity - only non player entities, accepts @s and @e *@s may be limited in this argument.

  * item - accepts minecraft itemstacks *may also support forge item stacks.

  * team - accepts scoreboard teams.
  * tag - accepts scoreboard tags.

  * color/colour - accepts minecraft chat colours, ex GREEN, LIME, LIGHT_PURPLE.

  * tellraw - only accepts a json tellraw.

  * bool - a binary boolean, accepts true, t, 1, yes, false, f, 0 or no.
  * string - only accepts a string "".
  * int - only accepts a integers, supports min and max argument criteria. Example `<int[min:0,max:64]:items>`
  * float - only accepts floats, supports min and max argument criteria.

- Criteria is where the requirements for the command to succeed are put. If criteria are not defined the player will not have to have any requirements to continue.

  Planned critera:
  * oplevel
  * team
  * tags

The results area is where you define the functions/scripts/other that are run based off various conditions and arguments above:

**definite** Unconditional, runs every single time the command is run.

-------------------------------------------------------------------------------

**failed**: Conditonal, runs if any criteria were not met.

**incorrectargs**: Conditional, runs if all criteria are met, but any argument (if any were used) has been entered incorrectly.

**success**: Conditional, runs if all criteria are met and all arguments (if any were used) are entered correctly.
*Any arguments set above will be inserted into functions/scripts/other in place of `<_"argName"_>`, it is up to the creator of the command to correctly insert the arguments in functions etc, a function called by a function will maintain argument data.*

*Any command has to have a "success" or "definite" function result defined. The "failed" and "incorrectargs" are optional but recommeneded for commands that don't use "definite" result. All functions executed are run from the perspective of the command sender wether that be a player, an entity or the console.


General Example Json:
```json
{
  "command":{
    "name":"commandname",
    "alias":"cmdname",
    "description":"this command does something",
    "argbuilder":"<bool:arg1><entity:arg2><int:arg3>",
    "criteria":{
      "team":"teamName",
      "tags":"aRandomTag, anotherTag",
      "oplevel":1
    },
    "result":{
      "success":{
        "function":"custom:command/success"
      },
      "definite":{
        "function":"custom:command/unconditional"
      },
     "incorrectargs":{
        "function":"custom:command/missingargs"
      },
      "failed":{
        "function":"custom:command/failed"
      }
    }
  }
}
```
Here is an example command Json for a tpa command: It only has a "success" and "incorrectargs" result because I've completely removed the criteria so this command can't get the "failed" result. 
```json
{
  "command":{
    "name":"tpa",
    "description":"Teleport to a friend",
    "argbuilder":"<pentity:arg1>",
    "criteria":{},
    "result":{
      "success":{
        "function":"tpaplugin:sendTpaRequest"
      },
     "incorrectargs":{
        "function":"tpaplugin:correctCommandUsage"
      }
    }
  }
}```

# Pipeline Event System
All pipeline events are stored as jsons. They are stored in `DATAPACK/data/custom/pipeline/events/`

- Criteria for triggering the event, functions in the same way as advancement criteria

- Result function that is run, generally from the criteria triggering player's perspective

General Example Json:
```json
{
  "event": {
    "criteria": {
      "first_criteria": {
        "trigger": "minecraft:consume_item",
        "conditions": {
            "item": {
              "item": "minecraft:apple"
            }
          }
      },
      "another_criteria": {
        "trigger": "minecraft:inventory_changed"
      }
    },
    "result": {
      "function": "custom:something"
    }
  }
}```
