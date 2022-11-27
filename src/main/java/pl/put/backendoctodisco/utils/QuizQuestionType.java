package pl.put.backendoctodisco.utils;

public enum QuizQuestionType {
    TYPE, CHOOSE, CONNECT;

    public static int getFrequency(QuizQuestionType type){
        return switch (type) {
            case TYPE -> 2;
            case CHOOSE -> 2;
            case CONNECT -> 1;
        };
    }

    public static int usedCards(QuizQuestionType type){
        return switch (type) {
            case TYPE -> 1;
            case CHOOSE -> 1;
            case CONNECT -> 5;
        };
    }

    public static int cardsNeeded(QuizQuestionType type){
        return QuizQuestionType.getFrequency(type)*QuizQuestionType.usedCards(type);
    }

    public static int sumCardsNeeded(){
        int sum=0;
        for (QuizQuestionType type : QuizQuestionType.values()) {
            sum+=cardsNeeded(type);
        }
        return sum;
    }
}
