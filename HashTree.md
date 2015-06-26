#java.util.Hashtable built into a tree

http://code.google.com/p/synthfuljava/source/browse/#svn/trunk/common/org/synthful/util

# Introduction #
Build a hierarchical hash tree where you could store and retrieve objects using dotted or oblique namespace -
  * get ("projects.deerfarm.employees.vellasammy.phone#")
  * put ("/customers/heinzes/contact", object)


# Details #
The separator is configurable. By default, it is an oblique (_forward slash_).
A tree can be grafted into an existing tree. For example, if treeA has an object accessible by
treeA.get("they/love/magpies")

and treeB has an object accessible by
treeB.get("/they/are/sweet")

grafting treeB into treeA with
treeA.get("they/love/magpies").put("because", treeB)

will consequently have an object retrievable by
treeA.get("they/love/magpies/because/they/are/sweet")

Note that, since it is a tree structure, the following would all have the same result:
  * treeA.get("they/love/magpies").put("because", treeB)
  * treeA.get("they/love").put("magpies/because", treeB)
  * treeA.get("they").put("love/magpies/because", treeB)
  * treeA.put("they/love/magpies/because", treeB)