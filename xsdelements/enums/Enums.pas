namespace proholz.xsdparser;
type
  SimpleTypeFinalEnum = public enum (list, &union, restriction, all); //#all

// *
//  * An {@link Enum} with all the possible values for the whiteSpace attribute.
//

  WhiteSpaceEnum = public enum(preserve, collapse, replace);

/**
 * An {@link Enum} with all the possible values for the blockDefault attribute of {@link XsdSchema}.
 */
  BlockDefaultEnum = public enum(default, extension, restriction, substitution, all); //#all

  BlockEnum = public enum(extension, restriction, substitution, all); //#all

  ComplexTypeBlockEnum = public enum(extension, restriction, all); //#all
  FinalDefaultEnum = public enum(default, extension, restriction, list, union , all); //#all
  FinalEnum = public enum(extension, restriction, all); //#all
  FormEnum = public enum(qualified, unqualified);
  UsageEnum = public enum(required, prohibited, optional);


end.