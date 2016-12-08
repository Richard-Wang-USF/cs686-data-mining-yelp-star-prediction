# run preprocssing first

# try lda
lda.correct.rate <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(classification.reviews.folds==i,arr.ind=TRUE)
  test.set  <- classification.reviews[testIndexes, ]
  train.set <- classification.reviews[-testIndexes, ]
  lda.fit=lda(Stars~.-PositiveNegativeSum, data = train.set)
  lda.pred=predict(lda.fit, test.set)$class
  lda.correct.rate[i] = mean(lda.pred==test.set$Stars)
}
mean(lda.correct.rate)
# we have 0.3934 much better

# try qda
qda.correct.rate <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(classification.reviews.folds==i,arr.ind=TRUE)
  test.set  <- classification.reviews[testIndexes, ]
  train.set <- classification.reviews[-testIndexes, ]
  qda.fit=qda(Stars~.-PositiveNegativeSum, data = train.set)
  qda.pred=predict(qda.fit, test.set)$class
  qda.correct.rate[i] = mean(qda.pred==test.set$Stars)
}
mean(qda.correct.rate)
# we have 0.359 good

# Logistic Regression 
# still working on it, does not work for now.
# library(VGAM)
# vglm.correct.rate <- c(0,0,0,0,0)
# for(i in 1:5){
#   testIndexes <- which(classification.reviews.folds==i,arr.ind=TRUE)
#   test.set  <- classification.reviews[testIndexes, ]
#   train.set <- classification.reviews[-testIndexes, ]
#   vglm.fit=vglm(Stars~Positive+Negative+TotalWords+Cool+Funny+Useful, family=multinomial, data = train.set)
#   vglm.pred = predict(vglm.fit, test.set)
#	vglm.correct.rate[i] = mean(vglm.pred == train.set$Stars)
# }
# mean(vglm.correct.rate)

# classification tree
require(tree)
tree.correct.rate <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(classification.reviews.folds==i,arr.ind=TRUE)
  test.set  <- classification.reviews[testIndexes, ]
  train.set <- classification.reviews[-testIndexes, ]
  classification.tree = tree(Stars~.-Stars,train.set)
  plot(classification.tree)
  text(classification.tree, pretty = 0)
  tree.pred = predict(classification.tree, test.set, type = "class")
  tree.correct.rate[i] = mean(tree.pred==test.set$Stars)
}
mean(tree.correct.rate)
# 0.3578

# try randomForest
require(randomForest)
rf.correct.rate <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(classification.reviews.folds==i,arr.ind=TRUE)
  test.set  <- classification.reviews[testIndexes, ]
  train.set <- classification.reviews[-testIndexes, ]
  rf.fit <- randomForest(Stars~.-Stars, data = train.set, mtry = 5, ntree = 100, importance = TRUE)
  plot(rf.fit)
  rf.pred <- predict(rf.fit, test.set, type = "class")
  rf.correct.rate[i] = mean(rf.pred==test.set$Stars)
}
mean(rf.correct.rate)
# 0.3612

# Naïve Bayes
library(class) 
library(e1071)
nb.correct.rate <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(classification.reviews.folds==i,arr.ind=TRUE)
  test.set  <- classification.reviews[testIndexes, ]
  train.set <- classification.reviews[-testIndexes, ]
  naivebayes.classifier = naiveBayes(train.set[,2:10], train.set[,1]) 
  naivebayes.pred = predict(naivebayes.classifier, test.set[,-1])
  nb.correct.rate[i] = mean(naivebayes.pred==train.set$Stars)
}
mean(nb.correct.rate)
# 0.2019

# SVM
library(e1071)
svm.correct.rate <- c(0,0,0,0,0)
for(i in 1:5){
  testIndexes <- which(regression.reviews.folds==i,arr.ind=TRUE)
  test.set  <- classification.reviews[testIndexes, ]
  train.set <- classification.reviews[-testIndexes, ]
  svmfit=svm(Stars~Positive+Negative,data=train.set,kernel="linear",cost=1,scale=FALSE)
  svm.pred=predict(svmfit, test.set)
  svm.correct.rate[i] = mean(svm.pred==test.set$Stars)
}
mean(svm.correct.rate)
# 0.3756