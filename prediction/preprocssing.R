#preprocessing

require(ISLR)
require(MASS)
require(boot)
library(class)

# set up working dir
setwd("/Users/richard/Dropbox/GitHub/cs686-data-mining-project/csv")

# load the reviews
regression.reviews <- read.csv("regression-reviews.csv")
classification.reviews <- read.csv("classification-reviews.csv")

# dim bad data
regression.reviews <- subset(regression.reviews, TotalWords > 0 & !(Positive == 0 & Negative == 0) )
classification.reviews <- subset(classification.reviews, TotalWords > 0 & !(Positive == 0 & Negative == 0) )

# balance class
reviews.1star <- subset(regression.reviews, Stars == 1)
reviews.1star <- reviews.1star[sample(nrow(reviews.1star), 1000),]
reviews.2star <- subset(regression.reviews, Stars == 2)
reviews.2star <- reviews.2star[sample(nrow(reviews.2star), 1000),]
reviews.3star <- subset(regression.reviews, Stars == 3)
reviews.3star <- reviews.3star[sample(nrow(reviews.3star), 1000),]
reviews.4star <- subset(regression.reviews, Stars == 4)
reviews.4star <- reviews.4star[sample(nrow(reviews.4star), 1000),]
reviews.5star <- subset(regression.reviews, Stars == 5)
reviews.5star <- reviews.5star[sample(nrow(reviews.5star), 1000),]
regression.reviews <- Reduce(function(x, y) merge(x, y, all=TRUE), list(reviews.1star, reviews.2star, reviews.3star, reviews.4star, reviews.5star))
regression.reviews <- regression.reviews[sample(nrow(regression.reviews), 5000), ]

reviews.1star <- subset(classification.reviews, Stars == "1stars")
reviews.1star <- reviews.1star[sample(nrow(reviews.1star), 1000),]
reviews.2star <- subset(classification.reviews, Stars == "2stars")
reviews.2star <- reviews.2star[sample(nrow(reviews.2star), 1000),]
reviews.3star <- subset(classification.reviews, Stars == "3stars")
reviews.3star <- reviews.3star[sample(nrow(reviews.3star), 1000),]
reviews.4star <- subset(classification.reviews, Stars == "4stars")
reviews.4star <- reviews.4star[sample(nrow(reviews.4star), 1000),]
reviews.5star <- subset(classification.reviews, Stars == "5stars")
reviews.5star <- reviews.5star[sample(nrow(reviews.5star), 1000),]
classification.reviews <- Reduce(function(x, y) merge(x, y, all=TRUE), list(reviews.1star, reviews.2star, reviews.3star, reviews.4star, reviews.5star))
classification.reviews <- classification.reviews[sample(nrow(classification.reviews), 5000), ]

# create folders for cross validation
regression.reviews.folds <- cut(seq(1,nrow(regression.reviews)),breaks=5,labels=FALSE)
classification.reviews.folds <- cut(seq(1,nrow(classification.reviews)),breaks=5,labels=FALSE)

# some plots about features
hist(regression.reviews$Positive, breaks=1000, xlim = c(0, 50))
hist(regression.reviews$Negative, breaks=1000, xlim = c(0, 30))
hist(regression.reviews$PositiveNegativeSum, breaks=1000, xlim = c(-20, 50))
hist(regression.reviews$TotalWords, breaks=1000)
# overall the features does work and the positive negative is well distributed

# some more box plots
boxplot(regression.reviews$Positive~regression.reviews$Stars, main="Star VS Positiveness", 
        xlab="Star", ylab="Positiveness", ylim = c(0, 20))
boxplot(regression.reviews$Negative~regression.reviews$Stars, main="Star VS Negativeness", 
        xlab="Star", ylab="Negativeness", ylim = c(0, 20))
boxplot(regression.reviews$PositiveNegativeSum~regression.reviews$Stars, main="Star VS Sum", 
        xlab="Star", ylab="Sum", ylim = c(-10, 20))