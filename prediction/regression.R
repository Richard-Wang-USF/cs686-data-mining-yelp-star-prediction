# Regression
# run preprocssing first before you run this

# try some simple linear regression on Total Words
lm.simple.correct.rate <- c(0,0,0,0,0)
lm.simple.mse <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(regression.reviews.folds==i,arr.ind=TRUE)
  test.set  <- regression.reviews[testIndexes, ]
  train.set <- regression.reviews[-testIndexes, ]
  lm.fit.simple = lm(Stars~.-Stars, data=train.set)
  lm.simple.probs=predict(lm.fit.simple, test.set)
  lm.simple.pred=ifelse(lm.simple.probs>=5,5,ifelse(lm.simple.probs<=1, 1,ceiling(lm.simple.probs)))
  lm.simple.mse[i] = mean(lm.fit.simple$residuals^2)
  lm.simple.correct.rate[i] = mean(lm.simple.pred==test.set$Stars)
}
mean(lm.simple.mse)
mean(lm.simple.correct.rate)
# 0.2892 correct rate with 1.36078 MSE

# with interaction
lm.interaction.correct.rate <- c(0,0,0,0,0)
lm.interaction.mse <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(regression.reviews.folds==i,arr.ind=TRUE)
  test.set  <- regression.reviews[testIndexes, ]
  train.set <- regression.reviews[-testIndexes, ]
  lm.fit.interaction = lm(Stars~
                       (Positive/PositiveWords)+(Negative/NegativeWords)+
                       (PositiveWords/TotalWords)+(NegativeWords/TotalWords)+
                       (PositiveWords/NegativeWords)+(Positive/Negative)+
                       Positive+Negative+PositiveNegativeSum+TotalWords+
                       Cool+Useful+Funny, data=train.set)
  lm.interaction.probs=predict(lm.fit.interaction, test.set)
  lm.interaction.pred=ifelse(lm.interaction.probs>=5,5,ifelse(lm.interaction.probs<=1, 1,ceiling(lm.interaction.probs)))
  lm.interaction.mse[i] = mean(lm.fit.interaction$residuals^2)
  lm.interaction.correct.rate[i] = mean(lm.interaction.pred==test.set$Stars)
}
mean(lm.interaction.correct.rate)
mean(lm.interaction.mse)
# we get 0.3062 correct rate with 1.251987 mse

# trees
require(tree)
set.seed(2)
tree.correct.rate <- c(0,0,0,0,0)
tree.mse <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(regression.reviews.folds==i,arr.ind=TRUE)
  test.set  <- regression.reviews[testIndexes, ]
  train.set <- regression.reviews[-testIndexes, ]
  regression.tree = tree(Stars~.-Stars,train.set)
  plot(regression.tree)
  text(regression.tree, pretty = 0)
  tree.pred = predict(regression.tree, test.set)
  tree.pred = ifelse(tree.pred>5,5,ifelse(tree.pred<1, 1,ceiling(tree.pred)))
  tree.mse[i] = mean((tree.pred-test.set$Stars)^2)
  tree.correct.rate[i] = mean(tree.pred==test.set$Stars)
}
mean(tree.correct.rate)
mean(tree.mse)
# tree 0.2854 correct rate with 1.5972 mse

# try randomForest
require(randomForest)
rf.correct.rate <- c(0,0,0,0,0)
rf.mse <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(regression.reviews.folds==i,arr.ind=TRUE)
  test.set  <- regression.reviews[testIndexes, ]
  train.set <- regression.reviews[-testIndexes, ]
  rf.fit <- randomForest(Stars~.-Stars, data = train.set, mtry = 10, ntree = 100, importance = TRUE)
  rf.pred <- predict(rf.fit, newdata = test.set)
  rf.pred = ifelse(rf.pred>5,5,ifelse(rf.pred<1, 1,ceiling(rf.pred)))
  rf.mse[i] = mean((rf.pred-test.set$Stars)^2)
  rf.correct.rate[i] = mean(rf.pred==test.set$Stars)
}
mean(rf.correct.rate)
mean(rf.mse)
# random forest is better 0.3084 with 1.6762 mse