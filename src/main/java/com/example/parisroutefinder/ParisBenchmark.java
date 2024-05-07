package com.example.parisroutefinder;

import javafx.scene.image.WritableImage;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Measurement(iterations=3)
@Warmup(iterations=1)
@Fork(value=1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class ParisBenchmark {
    public int[] data;
    ParisMap parisMap = new ParisMap(36, new WritableImage(1000,1000));
    @Setup(Level.Invocation)
    public void setup() {
        HelloController.helloController = new HelloController();
        MainController.mainController = new MainController();
        MainController.mainController.parisGraph = parisMap;
        MainController.mainController.landmarks = new ArrayList<>();
        //Adding everything for benchmarking
        Landmark l1 = new Landmark("Notre Dam", 364,238,10,true);
        Landmark l2 = new Landmark("Arc de Triomphe", 122,102,10,true);
        Landmark l3 = new Landmark("Sacre-Coeur", 324,8,10,true);
        Landmark l4 = new Landmark("Louvre", 299,187,10,true);
        Landmark l5 = new Landmark("Opera Garnier", 292,134,10,true);
        Landmark l6 = new Landmark("Catacombs", 283,364,10,true);
        Landmark l7 = new Landmark("Eiffel Tower", 119,203,10,true);

        Junction j1 = new Junction("j1", 106,192,0);
        Junction j2 = new Junction("j2", 123,177,0);
        Junction j3 = new Junction("j3", 150,165,0);
        Junction j4 = new Junction("j4", 150,117,0);

        Junction j5 = new Junction("j5", 240, 159, 0);
        Junction j6 = new Junction("j6", 233, 169, 0);
        Junction j7 = new Junction("j7", 294, 197, 0);
        Junction j8 = new Junction("j8", 243, 150, 0);
        Junction j9 = new Junction("j9", 299, 176, 0);

        Junction j10 = new Junction("j10", 288, 125, 0);
        Junction j11 = new Junction("j11", 323, 115, 0);
        Junction j12 = new Junction("j12", 325, 94, 0);
        Junction j13 = new Junction("j13", 290, 37, 0);
        Junction j14 = new Junction("j14", 301, 19, 0);
        Junction j15 = new Junction("j15", 309, 26, 0);

        Junction j16 = new Junction("j16", 372, 222, 0);
        Junction j17 = new Junction("j17", 253, 134, 0);

        Junction j18 = new Junction("j18", 142, 181,0);
        Junction j19 = new Junction("j19", 224, 177,0);
        Junction j20 = new Junction("j20", 273, 198,0);
        Junction j21 = new Junction("j21", 279, 190,0);
        Junction j22 = new Junction("j22", 315, 213,0);
        Junction j23 = new Junction("j23", 330, 230,0);
        Junction j24 = new Junction("j24", 369, 254,0);
        Junction j25 = new Junction("j25", 374, 244,0);
        Junction j26 = new Junction("j26", 258, 223,0);

        Junction j27 = new Junction("j27", 306, 335,0);
        Junction j28 = new Junction("j28", 341, 239,0);

        Street s1 = new Street("eiffelToj1",  l7,j1);
        Street s2 = new Street("j1Toj2",  j1,j2);
        Street s3 = new Street("j2Toj3",  j2,j3);
        Street s4 = new Street("j3Toj4",  j3,j4);
        Street s5 = new Street("j4ToArcDeTriomphe",  j4,l2);
        Street s6 = new Street("j4Toj5",j4,j5);
        Street s7 = new Street("j5Toj6",j5,j6);
        Street s8 = new Street("j6Toj7",j6,j7);
        Street s9 = new Street("j7ToLouvre",j7,l4);
        Street s10 = new Street("j5Toj8",j5,j8);
        Street s11 = new Street("j8Toj9",j8,j9);
        Street s12 = new Street("j9ToLouvre",j9,l4);
        Street s13 = new Street("j9toOperaGarnier",j9,l5);
        Street s14 = new Street("OperaGarnierToj10",l5,j10);
        Street s15 = new Street("j10Toj11",j10,j11);
        Street s16 = new Street("j11Toj12",j11,j12);
        Street s17 = new Street("j12Toj13",j12,j13);
        Street s18 = new Street("j13Toj14",j13,j14);
        Street s19 = new Street("j14Toj15",j14,j15);
        Street s20 = new Street("j14ToSacre",j15,l3);
        Street s21 = new Street("j7Toj16",j7,j16);
        Street s22 = new Street("j16ToNotreDam",j16,l1);
        Street s23 = new Street("j10Toj17", j10,j17);
        Street s24= new Street("j17Toj8",j17,j8);
        Street s25= new Street("EiffelToj18",l7,j18);
        Street s26= new Street("j18Toj19",j18,j19);
        Street s27= new Street("j19Toj20",j19,j20);
        Street s28= new Street("j20Toj21",j20,j21);
        Street s29= new Street("j21Toj7",j21,j7);
        Street s30= new Street("j20Toj22",j20,j22);
        Street s31= new Street("j22Toj23",j22,j23);
        Street s32= new Street("j23Toj24",j23,j24);
        Street s33 = new Street("j24Toj25",j24,j25);
        Street s34 = new Street("j25ToNotreDam",j25,l1);
        Street s35 = new Street("j20Toj26",j20,j26);
        Street s36 = new Street("j20Toj26",j19,j26);
        Street s37 = new Street("j20ToCatacombs",j26,l6);
        Street s38 = new Street("CatacombsToj27",l6,j27);
        Street s39 = new Street("j27Toj28",j27,j28);
        Street s40 = new Street("j28Toj24",j28,j24);
        Street s41 = new Street("j6Toj21",j6,j21);
        MainController.mainController.landmarks.add(l1);
        MainController.mainController.landmarks.add(l2);
        MainController.mainController.landmarks.add(l3);
        MainController.mainController.landmarks.add(l4);
        MainController.mainController.landmarks.add(l5);
        MainController.mainController.landmarks.add(l6);
        MainController.mainController.landmarks.add(l7);

        MainController.mainController.landmarks.add(j2);
        MainController.mainController.landmarks.add(j1);
        MainController.mainController.landmarks.add(j3);
        MainController.mainController.landmarks.add(j4);
        MainController.mainController.landmarks.add(j5);
        MainController.mainController.landmarks.add(j6);
        MainController.mainController.landmarks.add(j7);
        MainController.mainController.landmarks.add(j8);
        MainController.mainController.landmarks.add(j9);
        MainController.mainController.landmarks.add(j10);
        MainController.mainController.landmarks.add(j11);
        MainController.mainController.landmarks.add(j12);
        MainController.mainController.landmarks.add(j13);
        MainController.mainController.landmarks.add(j14);
        MainController.mainController.landmarks.add(j15);
        MainController.mainController.landmarks.add(j16);
        MainController.mainController.landmarks.add(j17);
        MainController.mainController.landmarks.add(j18);
        MainController.mainController.landmarks.add(j19);
        MainController.mainController.landmarks.add(j20);
        MainController.mainController.landmarks.add(j21);
        MainController.mainController.landmarks.add(j22);
        MainController.mainController.landmarks.add(j23);
        MainController.mainController.landmarks.add(j24);
        MainController.mainController.landmarks.add(j25);
        MainController.mainController.landmarks.add(j26);
        MainController.mainController.landmarks.add(j27);
        MainController.mainController.landmarks.add(j28);


        MainController.mainController.parisGraph.addLandmark(l1);
        MainController.mainController.parisGraph.addLandmark(l2);
        MainController.mainController.parisGraph.addLandmark(l3);
        MainController.mainController.parisGraph.addLandmark(l4);
        MainController.mainController.parisGraph.addLandmark(l5);
        MainController.mainController.parisGraph.addLandmark(l6);
        MainController.mainController.parisGraph.addLandmark(l7);

        MainController.mainController.parisGraph.addLandmark(j2);
        MainController.mainController.parisGraph.addLandmark(j1);
        MainController.mainController.parisGraph.addLandmark(j3);
        MainController.mainController.parisGraph.addLandmark(j4);
        MainController.mainController.parisGraph.addLandmark(j5);
        MainController.mainController.parisGraph.addLandmark(j6);
        MainController.mainController.parisGraph.addLandmark(j7);
        MainController.mainController.parisGraph.addLandmark(j8);
        MainController.mainController.parisGraph.addLandmark(j9);
        MainController.mainController.parisGraph.addLandmark(j10);
        MainController.mainController.parisGraph.addLandmark(j11);
        MainController.mainController.parisGraph.addLandmark(j12);
        MainController.mainController.parisGraph.addLandmark(j13);
        MainController.mainController.parisGraph.addLandmark(j14);
        MainController.mainController.parisGraph.addLandmark(j15);
        MainController.mainController.parisGraph.addLandmark(j16);
        MainController.mainController.parisGraph.addLandmark(j17);
        MainController.mainController.parisGraph.addLandmark(j18);
        MainController.mainController.parisGraph.addLandmark(j19);
        MainController.mainController.parisGraph.addLandmark(j20);
        MainController.mainController.parisGraph.addLandmark(j21);
        MainController.mainController.parisGraph.addLandmark(j22);
        MainController.mainController.parisGraph.addLandmark(j23);
        MainController.mainController.parisGraph.addLandmark(j24);
        MainController.mainController.parisGraph.addLandmark(j25);
        MainController.mainController.parisGraph.addLandmark(j26);
        MainController.mainController.parisGraph.addLandmark(j27);
        MainController.mainController.parisGraph.addLandmark(j28);

        MainController.mainController.parisGraph.addStreet(s1);
        MainController.mainController.parisGraph.addStreet(s2);
        MainController.mainController.parisGraph.addStreet(s3);
        MainController.mainController.parisGraph.addStreet(s4);
        MainController.mainController.parisGraph.addStreet(s5);
        MainController.mainController.parisGraph.addStreet(s6);
        MainController.mainController.parisGraph.addStreet(s7);
        MainController.mainController.parisGraph.addStreet(s8);
        MainController.mainController.parisGraph.addStreet(s9);
        MainController.mainController.parisGraph.addStreet(s10);
        MainController.mainController.parisGraph.addStreet(s11);
        MainController.mainController.parisGraph.addStreet(s12);
        MainController.mainController.parisGraph.addStreet(s13);
        MainController.mainController.parisGraph.addStreet(s14);
        MainController.mainController.parisGraph.addStreet(s15);
        MainController.mainController.parisGraph.addStreet(s16);
        MainController.mainController.parisGraph.addStreet(s17);
        MainController.mainController.parisGraph.addStreet(s18);
        MainController.mainController.parisGraph.addStreet(s19);
        MainController.mainController.parisGraph.addStreet(s20);
        MainController.mainController.parisGraph.addStreet(s21);
        MainController.mainController.parisGraph.addStreet(s22);
        MainController.mainController.parisGraph.addStreet(s23);
        MainController.mainController.parisGraph.addStreet(s24);
        MainController.mainController.parisGraph.addStreet(s25);
        MainController.mainController.parisGraph.addStreet(s26);
        MainController.mainController.parisGraph.addStreet(s27);
        MainController.mainController.parisGraph.addStreet(s28);
        MainController.mainController.parisGraph.addStreet(s29);
        MainController.mainController.parisGraph.addStreet(s30);
        MainController.mainController.parisGraph.addStreet(s31);
        MainController.mainController.parisGraph.addStreet(s32);
        MainController.mainController.parisGraph.addStreet(s33);
        MainController.mainController.parisGraph.addStreet(s34);
        MainController.mainController.parisGraph.addStreet(s35);
        MainController.mainController.parisGraph.addStreet(s36);
        MainController.mainController.parisGraph.addStreet(s37);
        MainController.mainController.parisGraph.addStreet(s38);
        MainController.mainController.parisGraph.addStreet(s39);
        MainController.mainController.parisGraph.addStreet(s40);
        MainController.mainController.parisGraph.addStreet(s41);

        MainController.mainController.landmarks.add(l1);
        MainController.mainController.landmarks.add(l2);
        MainController.mainController.parisGraph.addStreet(new Street("s1",l1, l2));
    }

    @Benchmark
    public void dijkstraBenchmark(){
        MainController.mainController.parisGraph.dijkstraShortestPath("Louvre", "Notre Dam", new ArrayList<>());
    }

    @Benchmark
    public void findingAllPaths(){
        MainController.mainController.parisGraph.findAllPaths("Louvre", "Notre Dam");
    }

    @Benchmark
    public void culturalDijkstra(){
        MainController.mainController.parisGraph.dijkstraShortestPathHistorical("Notre Dam", "Louvre");
    }

    @Benchmark
    public void addingStreet(){
        parisMap.addStreet(new Street("randomStreet",new Landmark("test1",4,4,0,true),new Landmark("test2",5,5,0,true)));
    }

    @Benchmark
    public void addingLandmark(){
        parisMap.addLandmark(new Landmark("test1",4,4,0,true));
    }
    public static void main(String[] args) throws
            RunnerException, IOException {
        Main.main(args);
    }
}
