package io.protostuff.parser;

import io.protostuff.proto3.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageParseListener extends Proto3BaseListener {

    private final Context context;

    public MessageParseListener(Context context) {
        this.context = context;
    }

    @Override
    public void enterMessageBlock(Proto3Parser.MessageBlockContext ctx) {
        Message message = new Message();
        context.push(message);
    }

    @Override
    public void exitMessageBlock(Proto3Parser.MessageBlockContext ctx) {
        Message message = context.pop(Message.class);
        AbstractUserTypeContainer messageParentBuilder = context.peek(AbstractUserTypeContainer.class);
        String name = ctx.NAME().getText();
        message.setName(name);
        messageParentBuilder.addMessage(message);
    }

    @Override
    public void enterMessageField(Proto3Parser.MessageFieldContext ctx) {
        MessageField messageField = new MessageField();
        context.push(messageField);
    }

    @Override
    public void exitMessageField(Proto3Parser.MessageFieldContext ctx) {
        MessageField messageField = context.pop(MessageField.class);
        Message message = context.peek(Message.class);
        String name = ctx.NAME().getText();
        String type = ctx.fieldType().getText();
        Integer tag = Integer.decode(ctx.INTEGER_VALUE().getText());
        messageField.setName(name);
        messageField.setTag(tag);
        messageField.setType(type);
        message.addField(messageField);
    }
}
