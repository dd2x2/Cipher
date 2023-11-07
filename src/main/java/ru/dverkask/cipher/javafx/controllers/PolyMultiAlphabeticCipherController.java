package ru.dverkask.cipher.javafx.controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.dverkask.cipher.AbstractCipher;
import ru.dverkask.cipher.CipherEnum;
import ru.dverkask.cipher.PolyMultiAlphabeticCipher;
import ru.dverkask.cipher.PolySingleAlphabeticCipher;
import ru.dverkask.cipher.utils.CipherUIHandler;
import ru.dverkask.cipher.utils.SelectedFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PolyMultiAlphabeticCipherController {
    private final List<String[]> ruCircuits = List.of(
            new String[]{"1О| цМ?фКЫФЮьтЯН=п{А[э#Дъ5;Б-кмбГ\"!в'РЭЗ&дВнЧ3+ажШзЕш2/$6Ё4)Т(у8ю.гХ~ро@ЪПи]яёСщ%йЦх9лчЙ:сеЬИЩ0>*ыУ,}Ж<Л7^`",
                    "ТГо х-ЛЙмвФысД(Ш%ю$Б`В9}з+еб|<Е=ЫИ;э[Х@Ю#,Ё*ртЧНРц)удьЗля&жашк~ёнК'О^П0щйЯУ{чф2Жъ]4/!Эи?:3гЬ75М1>А.ЪпЦЩ8С\"6",
                    "г*4-1!;'ШчУ>ЫНрэ%=Лд`Ж9Дсл5Хы[бвИуЦкп<7КЩЭ,)ъ~#Зцифо+|ОЕМяЮ/ю^:мжзё@ПТ(ЪФйА{тшЧ3х6&Гн ЯВ}$]Й2РаСь\"Ье0?.БЁ8щ"},
            new String[]{"гб5у*ЫОеШ}кЁ[;Ф@М0ъ:л$БпЖтос6КЬД/р'цдЛЧНЯйщхЮ1ПГ9Щьвш<Эюж|аыё4фЦЗ~иТн{>=ч?Йя3СУ](Ъ)Е8#!В-&Хз\"7^мИР%2`.+,Аэ ",
                    "{ОХйС]ФЯ;%о|Ж!сТ[х3ЦЮв-зЩПЬчыКЙэ ж\"И}.^гМБЧРщянёие4ак,Л85ю`т<рдпцЗЕ/)Ы:бЁ?Э109шГ&=А+#Д$м'@ъ67ЪфВ2Ш>ьулН*У(~",
                    "Й:шт\"ё#[8,ЗмсЧ/~Взькв(+@яаБэ|Ф1Иожд0КТП*97п5=чи{фА`Ж)ЯДС<ЩЁъЮ2'ОМг РЕУШ6хГХ4е>ц%юы^$л3!ЦЛЭ]}йн?б&;рЬЫщу.Н-Ъ"},
            new String[]{"$Яй:еЛ=\"ы&0Ы[ТьамФ| д(хи79)ЁрМ}гПГЗЧЙ1Е*Эу5БлДбъфз/Ъж3#{ОЦ6вХ>к'РошЬНК;ЩтШ%яюА~эЖщс,?Ю`В^.@<-2нС]ИчУ+ё4!ц8п",
                    "ЙЯЬа$]КзЛ'ИМФ*`5.Тынё3|д{%РяО ЕриУ/Ав<ЁпчЩгж>ДШл7ЧЪ6к)4^БтПцуф+;Ю!#бхшХ@ю0[Ц?2о8ьйщ-ъ9Ые~ЭсСГ=НВ\"&Жэ1м,З(}:",
                    "ч:7ЭртжЖ^о4 дШГё'НБи{а`у(АыцЛщ]5,вЦ81И9ЙЪ~зкЬг|/Д3=#йн!Пь6УшХЩъ@+%Я;0фР<&Ы)юх?К2лСЗ}бОспяе$ВЮЁЧ*[\"ЕФМТ>м.-э"}

    );

    private final List<String[]> enCircuits = List.of(
            new String[]{"tzR&jb4oN03|nxd/H]+ =K#5e}%:$@1`{cqu9raB[^F6.k<P?UfG2(8'hwLgIQAyWpXS7*DJi~!CV>Es);mOTM\"ZlvY-,",
                    "\"P%A,13MaihF#|)T:XU6zd2/{xO@-feWu.5o;*} q04QJ$]C^N>b8l&BInt+yps7~kYgL!Ew`[m?c(D=<H9KSvrGV'ZRj",
                    "G!jWMx<,0`-:IAs]8>}v9c[CBZVwf@zE&1$kUKa2(e)Tq5Nl;J\"{+% *#rugXb3n/?y.dDY^'P6QtR4iHS7=p~mF|hOoL"},
            new String[]{"swVj0v<67\"}mYoG+{2puy~'Hl`/53ci^%(KJTCDAOkN8QdEB9I:M]Lt-,XS#!fWge>) 4F[$n1PrxzU@=?R;*a|hZb.&q",
                    "[h$vzSH3`%+BWoyQ=64KmG<Tgn1(9ckf\"A#/,.bDe)^!p0YV]C{R*PMZ}8s'aJ-OUx2?Nu>w iEL@|&r;j:ltq~X5dIF7",
                    "P<pOj=4|C0/#o1Ne:H.nwaI>J d37{*R;Q'%^b+X,SYF8~!ZUu\"2t-hgyB&sT?krK[}Vi5xcv($WE@fq)]lD6zL9`AGmM"},
            new String[]{"-Z'igYSy1pFD*t2zaxVXM!GH=;sb86[#v7LWAl.r9~j(d<3B0,o>Pm{%KTQq$c|nu]+JIh`U O?E\"kfC}@&:)/5w^4eNR",
                    "c]GN$raf2IXB`Pw{CV-HFhDus|L07*<j@W.+pk1e5q /:td6;JUQ9',ZR(v4lK~bMg#O!?3[iz})yEn\"x&SYT=8Am%^>o",
                    "KM3V#cj!CN Xkm;t{r*yR/G2JoqW6d^+.h[Fa-5eb8I@:UEZf<S$}Y?1DpL,`AB4Pgsx'(~u0wi=Qz\"l&%7nv9]>H|TO)"}
    );

    private int key;

    private List<String[]> circuits;
    private String alphabet;
    @FXML
    private TextField textAlphabet;
    @FXML
    private TextField textKey;
    @FXML
    private ChoiceBox<String> choiceKey;
    @FXML
    private ChoiceBox<String> choiceAlphabet;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField encryptInputField;
    @FXML
    private TextField decryptInputField;
    @FXML
    private ChoiceBox<String> cipherType;
    @FXML
    private Label languageLabel1;
    @FXML
    private Label languageLabel2;
    @FXML
    private Label languageLabel3;
    @FXML
    private ImageView languageImageView1;
    @FXML
    private ImageView languageImageView2;
    @FXML
    private ImageView languageImageView3;
    @FXML
    private void initialize() {
        initializePolySingleAlphabeticCipherData();
        textKey.setDisable(false);
        textKey.setText("111");

        CipherUIHandler.choiceCipher(cipherType);
    }

    public void setKeyCharacterList(ChoiceBox<String> choiceBox) {
        circuits = choiceBox.equals("ruAlphabet")
                ? ruCircuits
                : enCircuits;

        key = Integer.parseInt(textKey.getText());
    }

    public void setAlphabet(ChoiceBox<String> choiceBox) {
        if (choiceAlphabet.getValue().equals("Свой алфавит")) {
            alphabet = textAlphabet.getText();
        } else {
            alphabet = choiceBox.getValue().equals("ruAlphabet")
                    ? CipherEnum.RU_ALPHABET.getValue()
                    : CipherEnum.EN_ALPHABET.getValue();
        }
    }

    public void selectEncryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(encryptInputField);
    }

    public void selectDecryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(decryptInputField);
    }

    public void encryptBtnAction(ActionEvent actionEvent) {
        setAlphabet(choiceAlphabet);
        setKeyCharacterList(choiceAlphabet);
        AbstractCipher polyMultiAlphabeticCipher =
                new PolyMultiAlphabeticCipher(alphabet, circuits, key);

        resultArea.setText(
                polyMultiAlphabeticCipher.encrypt(encryptInputField.getText())
        );
    }

    public void decryptBtnAction(ActionEvent actionEvent) {
        setAlphabet(choiceAlphabet);
        setKeyCharacterList(choiceAlphabet);
        AbstractCipher polyMultiAlphabeticCipher =
                new PolyMultiAlphabeticCipher(alphabet, circuits, key);

        resultArea.setText(
                polyMultiAlphabeticCipher.decrypt(decryptInputField.getText())
        );
    }

    public void initializePolySingleAlphabeticCipherData() {
        choiceAlphabet.setValue("ruAlphabet");
        choiceKey.setValue("Свой ключ");
        cipherType.setValue("PolyMulti");

        setRuImages();

        choiceAlphabet.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateLanguageAndImage());
        choiceKey.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateLanguageAndImage());
    }

    private void updateLanguageAndImage() {
        if (choiceAlphabet.getValue().equals("ruAlphabet")) {
            setRuImages();
        } else if (choiceAlphabet.getValue().equals("enAlphabet")) {
            setEnImages();
        }
    }

    private void setRuImages() {
        languageLabel1.setText("Русский алфавит и контур (1):");
        languageImageView1.setImage(new Image("/images/rualphabet10.jpg"));
        languageLabel2.setText("Русский алфавит и контур (2)");
        languageImageView2.setImage(new Image("/images/rualphabet11.jpg"));
        languageLabel3.setText("Русский алфавит и контур (3)");
        languageImageView3.setImage(new Image("/images/rualphabet12.jpg"));
    }

    private void setEnImages() {
        languageLabel1.setText("Английский алфавит и контур (1):");
        languageImageView1.setImage(new Image("/images/enalphabet10.jpg"));
        languageLabel2.setText("Английский алфавит и контур (2)");
        languageImageView2.setImage(new Image("/images/enalphabet11.jpg"));
        languageLabel3.setText("Английский алфавит и контур (3)");
        languageImageView3.setImage(new Image("/images/enalphabet12.jpg"));
    }
}
