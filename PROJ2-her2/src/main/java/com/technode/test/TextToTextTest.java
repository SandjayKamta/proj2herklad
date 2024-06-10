package com.technode.test;

import com.technode.QueryResolution.QueryResolutionForm;
import com.technode.QueryResolution.QueryResolutionStrategy;
import com.technode.QueryResolution.TextToText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextToTextTest {
    @Test
    public void testKnownInputsDecisionTable1() {
        QueryResolutionStrategy strategy = new TextToText();

        assertEquals("Hallo, hoe kan ik je helpen?", strategy.resolve(new QueryResolutionForm<>("Hallo")).resultData());
        assertEquals("Mijn naam is Chatbot 42", strategy.resolve(new QueryResolutionForm<>("Naam")).resultData());
        assertEquals("Mijn favoriete kleur is blauw", strategy.resolve(new QueryResolutionForm<>("Kleur")).resultData());
        assertEquals("Mijn favoriete eten is pizza", strategy.resolve(new QueryResolutionForm<>("Eten")).resultData());
        assertEquals("Ik begrijp je niet, probeer het opnieuw", strategy.resolve(new QueryResolutionForm<>("Onbekend")).resultData());
    }

    @Test
    public void testKnownInputsDecisionTable2() {
        QueryResolutionStrategy strategy = new TextToText();

        assertEquals("Hallo, hoe kan ik je helpen?", strategy.resolve(new QueryResolutionForm<>("hallo")).resultData());
        assertEquals("Mijn naam is Chatbot 42", strategy.resolve(new QueryResolutionForm<>("NAAM")).resultData());
        assertEquals("Mijn favoriete kleur is blauw", strategy.resolve(new QueryResolutionForm<>("KLEUR")).resultData());
        assertEquals("Mijn favoriete eten is pizza", strategy.resolve(new QueryResolutionForm<>("ETEN")).resultData());
        assertEquals("Ik begrijp je niet, probeer het opnieuw", strategy.resolve(new QueryResolutionForm<>("Onbekend")).resultData());
    }

    @Test
    public void testUnknownInputs() {
        QueryResolutionStrategy strategy = new TextToText();

        assertEquals("Ik begrijp je niet, probeer het opnieuw", strategy.resolve(new QueryResolutionForm<>("Hoe laat")).resultData());
        assertEquals("Ik begrijp je niet, probeer het opnieuw", strategy.resolve(new QueryResolutionForm<>("Vragen")).resultData());
    }

    @Test
    public void testEdgeCases() {
        QueryResolutionStrategy strategy = new TextToText();

        // Empty string
        assertEquals("Ik begrijp je niet, probeer het opnieuw", strategy.resolve(new QueryResolutionForm<>("")).resultData());

        // String with multiple known words
        assertEquals("Hallo, hoe kan ik je helpen?", strategy.resolve(new QueryResolutionForm<>("Hallo naam")).resultData());
    }

    @Test
    public void testConditionCoverage() {
        QueryResolutionStrategy strategy = new TextToText();

        assertEquals("Hallo, hoe kan ik je helpen?", strategy.resolve(new QueryResolutionForm<>("Hallo")).resultData());
        assertEquals("Mijn naam is Chatbot 42", strategy.resolve(new QueryResolutionForm<>("Naam")).resultData());
        assertEquals("Mijn favoriete kleur is blauw", strategy.resolve(new QueryResolutionForm<>("Kleur")).resultData());
        assertEquals("Mijn favoriete eten is pizza", strategy.resolve(new QueryResolutionForm<>("Eten")).resultData());
        assertEquals("Ik begrijp je niet, probeer het opnieuw", strategy.resolve(new QueryResolutionForm<>("BlahBlah")).resultData());
    }

}
