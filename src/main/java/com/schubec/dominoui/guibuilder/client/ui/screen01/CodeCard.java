package com.schubec.dominoui.guibuilder.client.ui.screen01;

import static org.jboss.gwt.elemento.core.Elements.input;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.notifications.Notification;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.domino.ui.utils.DominoDom;
import org.dominokit.domino.ui.utils.DominoElement;
import org.gwtproject.safehtml.shared.SafeHtmlBuilder;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.gwt.elemento.core.InputType;

import elemental2.dom.ClipboardEvent;
import elemental2.dom.DomGlobal;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLPreElement;
import jsinterop.base.Js;

public class CodeCard extends BaseDominoElement<HTMLDivElement, CodeCard> {

    private HTMLInputElement copyInput = input(InputType.textarea)
            .style("visibility:hidden; width: 0px; height: 0px;").asElement();
    private String code;
    private HTMLPreElement codeBlock = Elements.pre().css("prettyprint").asElement();
    private Card card = Card.create("Source Code")
            .setCollapsible()
            .hide()
            .appendChild(codeBlock);

    public CodeCard(String title) {
        copyInput.value = code;
        card.setTitle(title);
        card.addHeaderAction(Icons.ALL.content_copy()
                .setTooltip("Copy code"), evt -> {
            copyInput.select();
            EventListener copyListener = e -> {
                ClipboardEvent clipboardEvent = Js.uncheckedCast(e);
                clipboardEvent.clipboardData.setData("text/plain", code);
                e.preventDefault();
            };
            DomGlobal.document.addEventListener("copy", copyListener);
            DominoDom.document.execCommand("copy");
            DomGlobal.document.removeEventListener("copy", copyListener);
            Notification.createInfo("Code copied to clipboard").show();
        });

        card.appendChild(copyInput);

        init(this);
    }



    public static CodeCard createCodeCard(String title, String code, String lang) {
        CodeCard codeCard = new CodeCard(title);
        DominoElement.of(codeCard.codeBlock)
                .clearElement()
                .setInnerHtml(PR.prettyPrintOne(code, lang, false));
        codeCard.code = code;
        return codeCard;
    }

    

    public CodeCard setCode(String code) {
        DominoElement.of(this.codeBlock).setInnerHtml(PR.prettyPrintOne(code, null, false));
        this.code = code;
        return this;
    }


    public CodeCard setTitle(String title) {
        card.setTitle(title);
        return this;
    }

    public CodeCard setDescription(String description) {
        card.setDescription(description);
        return this;
    }

    public Card getCard() {
        return card;
    }

    
    public static HTMLPreElement preBlock(String code) {
        return Elements.pre().css("prettyprint").innerHtml(new SafeHtmlBuilder().appendHtmlConstant(PR.prettyPrintOne(code, null, false)).toSafeHtml()).asElement();
    }

    @Override
    public HTMLDivElement asElement() {
        return card.asElement();
    }
}
