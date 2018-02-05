rm -rf doro_juso.txt
ln -s ./doro_juso/gun_X.txt doro_juso.txt
mysqlimport -u root -p egov_common --fields-terminated-by="|" --lines-terminated-by="\n" /home/wso2/doro_juso.txt