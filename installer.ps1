###########################################
#
#
#          CRYPTOKEEP INSTALLER (WIN)
#
#          @Author: Husam Abdalla
#          3/5/2022
#          
#
#
###########################################

# Get system info and create globals.
# GLOBALS:
# Hostname.
# Current user.
# Destination.
# bin and src.

$user =$env:UserName
$BIN = 'C:\Packages\Crypto-Keep\bin\'
$SRC = 'C:\Packages\Crypto-Keep\src\'
$DESKTOP = "C:\Users\$env:USERNAME\Desktop\"

# Check if Java is installed.

function checkJava(){

    java -version
    
    if(!$?){
    [System.Windows.Forms.MessageBox]::Show("Java is not installed on this machine, please install java to proceed")
    Write-Host 'Java is not installed! Installation failed'

    }

}

# Check if Binaries are present or only source, if only source compile.

function checkFiles(){

    $FILES = $(ls *.class)
    
    if($FILES -eq $null){
    
        javac PassMan.java

    }


}

# Create the directories:

function MakeDirs(){

mkdir $SRC
mkdir $BIN

Move-Item *.java -Destination $SRC
Move-Item * -Destination $BIN

}

# Else if jar not present, check for .bat, if present, create shortcut.

function CheckJar(){

    cd $BIN
    
    $flag = $(ls *.bat)
    
    if($flag -eq $null){

        # If nothing is present, create .cmd and create shortcut.
    
        echo 'java PassMan' >> Cryptokeep.cmd
        
        $ComObj = New-Object -ComObject WScript.shell
        $ShortCut = $ComObj.CreateShortcut("$Home\Desktop\Cryptokeep.lnk")
        $ShortCut.TargetPath = $BIN+'Cryptokeep.cmd'
        $ShortCut.Save()


    }
    else{
    
        $ComObj = New-Object -ComObject WScript.shell

        $ShortCut = $ComObj.CreateShortcut("$Home\Desktop\Cryptokeep.lnk")
        $ShortCut.TargetPath = $BIN+'Cryptokeep.bat'
        $ShortCut.Save()


    }

}

checkJava
checkFiles
MakeDirs
CheckJar

# Exit
pause
