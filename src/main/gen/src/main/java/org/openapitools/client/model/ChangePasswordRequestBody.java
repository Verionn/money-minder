/*
 * MoneyMinder - Rest api
 * Api for MoneyMinder project
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * ChangePasswordRequestBody
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-03-16T00:31:43.951428+01:00[Europe/Warsaw]")
public class ChangePasswordRequestBody {
  public static final String SERIALIZED_NAME_OLD_PASSWORD = "oldPassword";
  @SerializedName(SERIALIZED_NAME_OLD_PASSWORD)
  private String oldPassword;

  public static final String SERIALIZED_NAME_REPEAT_OLD_PASSWORD = "repeatOldPassword";
  @SerializedName(SERIALIZED_NAME_REPEAT_OLD_PASSWORD)
  private String repeatOldPassword;

  public static final String SERIALIZED_NAME_NEW_PASSWORD = "newPassword";
  @SerializedName(SERIALIZED_NAME_NEW_PASSWORD)
  private String newPassword;

  public ChangePasswordRequestBody() {
  }

  public ChangePasswordRequestBody oldPassword(String oldPassword) {
    
    this.oldPassword = oldPassword;
    return this;
  }

   /**
   * Get oldPassword
   * @return oldPassword
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "123456", required = true, value = "")

  public String getOldPassword() {
    return oldPassword;
  }


  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }


  public ChangePasswordRequestBody repeatOldPassword(String repeatOldPassword) {
    
    this.repeatOldPassword = repeatOldPassword;
    return this;
  }

   /**
   * Get repeatOldPassword
   * @return repeatOldPassword
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "123456", required = true, value = "")

  public String getRepeatOldPassword() {
    return repeatOldPassword;
  }


  public void setRepeatOldPassword(String repeatOldPassword) {
    this.repeatOldPassword = repeatOldPassword;
  }


  public ChangePasswordRequestBody newPassword(String newPassword) {
    
    this.newPassword = newPassword;
    return this;
  }

   /**
   * Get newPassword
   * @return newPassword
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(example = "qwerty", required = true, value = "")

  public String getNewPassword() {
    return newPassword;
  }


  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChangePasswordRequestBody changePasswordRequestBody = (ChangePasswordRequestBody) o;
    return Objects.equals(this.oldPassword, changePasswordRequestBody.oldPassword) &&
        Objects.equals(this.repeatOldPassword, changePasswordRequestBody.repeatOldPassword) &&
        Objects.equals(this.newPassword, changePasswordRequestBody.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oldPassword, repeatOldPassword, newPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChangePasswordRequestBody {\n");
    sb.append("    oldPassword: ").append(toIndentedString(oldPassword)).append("\n");
    sb.append("    repeatOldPassword: ").append(toIndentedString(repeatOldPassword)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("oldPassword");
    openapiFields.add("repeatOldPassword");
    openapiFields.add("newPassword");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("oldPassword");
    openapiRequiredFields.add("repeatOldPassword");
    openapiRequiredFields.add("newPassword");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to ChangePasswordRequestBody
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (ChangePasswordRequestBody.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in ChangePasswordRequestBody is not found in the empty JSON string", ChangePasswordRequestBody.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ChangePasswordRequestBody.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ChangePasswordRequestBody` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ChangePasswordRequestBody.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if ((jsonObj.get("oldPassword") != null && !jsonObj.get("oldPassword").isJsonNull()) && !jsonObj.get("oldPassword").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `oldPassword` to be a primitive type in the JSON string but got `%s`", jsonObj.get("oldPassword").toString()));
      }
      if ((jsonObj.get("repeatOldPassword") != null && !jsonObj.get("repeatOldPassword").isJsonNull()) && !jsonObj.get("repeatOldPassword").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `repeatOldPassword` to be a primitive type in the JSON string but got `%s`", jsonObj.get("repeatOldPassword").toString()));
      }
      if ((jsonObj.get("newPassword") != null && !jsonObj.get("newPassword").isJsonNull()) && !jsonObj.get("newPassword").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `newPassword` to be a primitive type in the JSON string but got `%s`", jsonObj.get("newPassword").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ChangePasswordRequestBody.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ChangePasswordRequestBody' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ChangePasswordRequestBody> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ChangePasswordRequestBody.class));

       return (TypeAdapter<T>) new TypeAdapter<ChangePasswordRequestBody>() {
           @Override
           public void write(JsonWriter out, ChangePasswordRequestBody value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ChangePasswordRequestBody read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ChangePasswordRequestBody given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ChangePasswordRequestBody
  * @throws IOException if the JSON string is invalid with respect to ChangePasswordRequestBody
  */
  public static ChangePasswordRequestBody fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ChangePasswordRequestBody.class);
  }

 /**
  * Convert an instance of ChangePasswordRequestBody to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

