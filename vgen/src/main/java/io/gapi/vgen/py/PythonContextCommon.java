/* Copyright 2016 Google Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gapi.vgen.py;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.List;

/**
 * A class that provides helper methods for snippet files generating Python code
 * to get data and perform data transformations that are difficult or messy to do
 * in the snippets themselves.
 */
public class PythonContextCommon {

  /**
   * A set of python keywords and built-ins.
   * Built-ins derived from: https://docs.python.org/2/library/functions.html
   */
  private static final ImmutableSet<String> KEYWORD_BUILT_IN_SET =
      ImmutableSet.<String>builder()
      .add("and", "as", "assert", "break", "class", "continue", "def", "del", "elif", "else",
          "except", "exec", "finally", "for", "from", "global", "if", "import", "in", "is",
          "lambda", "not", "or", "pass", "print", "raise", "return", "try", "while", "with",
          "yield", "abs", "all", "any", "basestring", "bin", "bool", "bytearray", "callable", "chr",
          "classmethod", "cmp", "compile", "complex", "delattr", "dict", "dir", "divmod",
          "enumerate", "eval", "execfile", "file", "filter", "float", "format", "frozenset",
          "getattr", "globals", "hasattr", "hash", "help", "hex", "id", "input", "int",
          "isinstance", "issubclass", "iter", "len", "list", "locals", "long", "map", "max",
          "memoryview", "min", "next", "object", "oct", "open", "ord", "pow", "print", "property",
          "range", "raw_input", "reduce", "reload", "repr", "reversed", "round", "set", "setattr",
          "slice", "sorted", "staticmethod", "str", "sum", "super", "tuple", "type", "unichr",
          "unicode", "vars", "xrange", "zip", "__import__")
      .build();

  // Snippet Helpers
  // ===============

  /**
   * Return a non-conflicting safe name if name is a python built-in.
   */
  public String wrapIfKeywordOrBuiltIn(String name) {
    if (KEYWORD_BUILT_IN_SET.contains(name)) {
      return name + "_";
    }
    return name;
  }

  /*
   * Convert the content string into a commented block that can be directly printed out in the
   * generated py files.
   */
  public List<String> convertToCommentedBlock(String content) {
    if (Strings.isNullOrEmpty(content)) {
      return ImmutableList.<String>of();
    }
    List<String> comments = Splitter.on("\n").splitToList(content);
    ImmutableList.Builder<String> builder = ImmutableList.builder();
    if (comments.size() > 1) {
      builder.add("\"\"\"");
      for (String comment : comments) {
        builder.add(comment);
      }
      builder.add("\"\"\"");
    } else {
      // one-line comment.
      builder.add("\"\"\"" + content + "\"\"\"");
    }
    return builder.build();
  }
}