package advanced;

import java.util.ArrayList;
import java.util.List;

public record Card(Suit suit, String face, int rank) {

    public enum Suit {
        CLUB('♣'),
        DIAMOND('♦'),
        HEART('♥'),
        SPADE('♠');

        private final char symbol;

        Suit(char symbol) {
            this.symbol = symbol;
        }

        public char getImage() {
            return symbol;
        }
    }

    @Override
    public String toString() {
        String faceStr = face.equals("10") ? "10" : face.substring(0, 1);
        return "%s%c(%d)".formatted(faceStr, suit.getImage(), rank);
    }

    public static Card getNumericCard(Suit suit, int cardNumber) {
        if (cardNumber >= 2 && cardNumber <= 10) {
            String face = String.valueOf(cardNumber);
            int rank = cardNumber - 2;
            return new Card(suit, face, rank);
        }
        System.err.println("Invalid numeric card: " + cardNumber);
        return null;
    }

    public static Card getFaceCard(Suit suit, char abbrev) {
        switch (Character.toUpperCase(abbrev)) {
            case 'J' -> {
                return new Card(suit, "J", 9);
            }
            case 'Q' -> {
                return new Card(suit, "Q", 10);
            }
            case 'K' -> {
                return new Card(suit, "K", 11);
            }
            case 'A' -> {
                return new Card(suit, "A", 12);
            }
            default -> {
                System.err.println("Invalid face card abbreviation: " + abbrev);
                return null;
            }
        }
    }

    public static Card from(Suit suit, String faceStr) {
        if (faceStr == null || faceStr.isEmpty())
            return null;
        if (faceStr.matches("\\d+")) {
            int num = Integer.parseInt(faceStr);
            return getNumericCard(suit, num);
        }
        if (faceStr.length() == 1) {
            return getFaceCard(suit, faceStr.charAt(0));
        }
        System.err.println("Invalid card face: " + faceStr);
        return null;
    }

    public static List<Card> getStandardDeck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (int i = 2; i <= 10; i++) {
                Card card = getNumericCard(suit, i);
                if (card != null) {
                    deck.add(card);
                }
            }
            deck.add(getFaceCard(suit, 'J'));
            deck.add(getFaceCard(suit, 'Q'));
            deck.add(getFaceCard(suit, 'K'));
            deck.add(getFaceCard(suit, 'A'));
        }
        return deck;
    }

    public static void printDeck(List<Card> deck) {
        for (Card card : deck) {
            System.out.print(card + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Card> deck = getStandardDeck();
        printDeck(deck);
        System.out.println("hello world from intellij");


        
    }
}