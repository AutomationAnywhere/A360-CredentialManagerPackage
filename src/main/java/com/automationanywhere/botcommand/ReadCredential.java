package com.automationanywhere.botcommand;

import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.core.security.SecureString;

import static com.automationanywhere.commandsdk.model.DataType.STRING;

//BotCommand makes a class eligible for being considered as an action.
@BotCommand

//CommandPks adds required information to be dispalable on GUI.
@CommandPkg(
        //Unique name inside a package and label to display.
        name = "ReadCredential", label = "[[ReadCredential.label]]",
        node_label = "[[ReadCredential.node_label]]",  description = "[[ReadCredential.description]]", icon = "safe_icon.svg",

        //Return type information. return_type ensures only the right kind of variable is provided on the UI.
        return_label = "[[ReadCredential.return_label]]", return_type = STRING, return_required = true)
public class ReadCredential {
    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public StringValue action(
            //Idx 1 would be displayed first, with a text box for entering the value.
            @Idx(index = "1", type = AttributeType.CREDENTIAL)
            //UI labels.
            @Pkg(label = "[[ReadCredential.CMInput.label]]")
            //Ensure that a validation error is thrown when the value is null.
            @NotEmpty
                    SecureString CMInput) {

        //Internal validation, to disallow empty inputs. No null check needed as we have NotEmpty on CMInput.
        String result="";
        try {
            //Convert SecureString type to String
            result = CMInput.getInsecureString();
        } catch (Exception e) {
            //Print exception for user
            throw new BotCommandException("Unable to read credential from vault");
        }
        //Return String Value result.
        return new StringValue(result);
    }
}
