#!/usr/bin/env bash

echo $(awslocal s3 mb s3://resource)

echo $(awslocal s3 ls)