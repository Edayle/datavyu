/*
    File: LayerCanvas.m
Abstract: Native code that attaches a CoreAnimation layer to an AWT Canvas.
 Version: 2.0

Disclaimer: IMPORTANT:  This Apple software is supplied to you by Apple
Inc. ("Apple") in consideration of your agreement to the following
terms, and your use, installation, modification or redistribution of
this Apple software constitutes acceptance of these terms.  If you do
not agree with these terms, please do not use, install, modify or
redistribute this Apple software.

In consideration of your agreement to abide by the following terms, and
subject to these terms, Apple grants you a personal, non-exclusive
license, under Apple's copyrights in this original Apple software (the
"Apple Software"), to use, reproduce, modify and redistribute the Apple
Software, with or without modifications, in source and/or binary forms;
provided that if you redistribute the Apple Software in its entirety and
without modifications, you must retain this notice and the following
text and disclaimers in all such redistributions of the Apple Software.
Neither the name, trademarks, service marks or logos of Apple Inc. may
be used to endorse or promote products derived from the Apple Software
without specific prior written permission from Apple.  Except as
expressly stated in this notice, no other rights or licenses, express or
implied, are granted by Apple herein, including but not limited to any
patent rights that may be infringed by your derivative works or by other
works in which the Apple Software may be incorporated.

The Apple Software is provided by Apple on an "AS IS" basis.  APPLE
MAKES NO WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE, REGARDING THE APPLE SOFTWARE OR ITS USE AND
OPERATION ALONE OR IN COMBINATION WITH YOUR PRODUCTS.

IN NO EVENT SHALL APPLE BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL
OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION,
MODIFICATION AND/OR DISTRIBUTION OF THE APPLE SOFTWARE, HOWEVER CAUSED
AND WHETHER UNDER THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE),
STRICT LIABILITY OR OTHERWISE, EVEN IF APPLE HAS BEEN ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.

Copyright (C) 2011 Apple Inc. All Rights Reserved.

*/

#import <Cocoa/Cocoa.h>
#import <Quartz/Quartz.h>
#import <QTKit/QTKit.h>
#import <jawt_md.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

//#include "org_datavyu_plugins_qtkitplayer_QTKitPlayer.h"
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_datavyu_plugins_qtkitplayer_QTKitPlayer */

#ifndef _Included_org_datavyu_plugins_qtkitplayer_QTKitPlayer
#define _Included_org_datavyu_plugins_qtkitplayer_QTKitPlayer
#ifdef __cplusplus
extern "C" {
#endif
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_FOCUS_TRAVERSABLE_UNKNOWN
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_FOCUS_TRAVERSABLE_UNKNOWN 0L
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_FOCUS_TRAVERSABLE_DEFAULT
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_FOCUS_TRAVERSABLE_DEFAULT 1L
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_FOCUS_TRAVERSABLE_SET
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_FOCUS_TRAVERSABLE_SET 2L
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_TOP_ALIGNMENT
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_TOP_ALIGNMENT 0.0f
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_CENTER_ALIGNMENT
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_CENTER_ALIGNMENT 0.5f
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_BOTTOM_ALIGNMENT
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_BOTTOM_ALIGNMENT 1.0f
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_LEFT_ALIGNMENT
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_LEFT_ALIGNMENT 0.0f
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_RIGHT_ALIGNMENT
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_RIGHT_ALIGNMENT 1.0f
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_serialVersionUID
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_serialVersionUID -7644114512714619750LL
#undef org_datavyu_plugins_qtkitplayer_QTKitPlayer_serialVersionUID
#define org_datavyu_plugins_qtkitplayer_QTKitPlayer_serialVersionUID -2284879212465893870LL
    /*
     * Class:     org_datavyu_plugins_qtkitplayer_QTKitPlayer
     * Method:    addNativeCoreAnimationLayer
     * Signature: ()V
     */
    JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_addNativeCoreAnimationLayer
    (JNIEnv *, jobject, jstring, jint);
    
    /*
     * Class:     org_datavyu_plugins_qtkitplayer_QTKitPlayer
     * Method:    stop
     * Signature: ()V
     */
    JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_stop
    (JNIEnv *, jobject, jint);
    
    /*
     * Class:     org_datavyu_plugins_qtkitplayer_QTKitPlayer
     * Method:    play
     * Signature: ()V
     */
    JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_play
    (JNIEnv *, jobject, jint);
    
    /*
     * Class:     org_datavyu_plugins_qtkitplayer_QTKitPlayer
     * Method:    setTime
     * Signature: (J)V
     */
    JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_setTime
    (JNIEnv *, jobject, jlong, jint);
    
    /*
     * Class:     org_datavyu_plugins_qtkitplayer_QTKitPlayer
     * Method:    setVolume
     * Signature: (F)V
     */
    JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_setVolume
    (JNIEnv *, jobject, jfloat, jint);
    
    /*
     * Class:     org_datavyu_plugins_qtkitplayer_QTKitPlayer
     * Method:    loadMovie
     * Signature: (Ljava/lang/String;)V
     */
    JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_loadMovie
    (JNIEnv *, jobject, jstring, jint);
    
    JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_release
    (JNIEnv *, jobject, jint);
    
    JNIEXPORT jdouble JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_getMovieWidth
    (JNIEnv *env, jobject canvas, jint movieId);
    
#ifdef __cplusplus
}
#endif
#endif



// forward declaration
@interface MovieLayer : CALayer { }
- (id) initWithFile:(NSString*)file;
@end

NSMutableArray* movies;
NSMutableArray* movieLayers;
NSMutableArray* fpses;

QTMovie *GetQtMovie(jint idx) {
//    NSLog(@"Getting movie at idx: %d, currentCount: %lu", (int)idx, (unsigned long)[movies count]);
    QTMovie *pointer = [movies objectAtIndex:(int)idx];
    return pointer;
}

MovieLayer *GetMovieLayer(jint idx) {
    NSLog(@"Getting movieLayer at idx: %d, currentCount: %lu", (int)idx, (unsigned long)[movieLayers count]);

    return [movieLayers objectAtIndex:(int)idx];
}

NSString *ConvertToNSString(JNIEnv *env, jstring str)
{
    if (str == NULL)
    {
        return NULL;
    }
    
    const jchar *chars = (*env)->GetStringChars(env, str, NULL);
    NSString *myNSString =
    [NSString stringWithCharacters:(UniChar *)chars  length:(*env)->GetStringLength(env, str)];
    (*env)->ReleaseStringChars(env, str, chars);
    
    return myNSString;
}

JNIEXPORT jdouble JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_getMovieHeight
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    
    NSSize sourceSize = [[[GetQtMovie(movieId) movieAttributes] valueForKey:@"QTMovieNaturalSizeAttribute"] sizeValue];
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    
    return sourceSize.height;
    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT jlong JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_getCurrentTime
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    QTTime newQTTime = [GetQtMovie(movieId) currentTime];
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);

    long long time = (newQTTime.timeValue * 1000.0f) / newQTTime.timeScale;
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);

    
    return time;
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT jlong JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_getDuration
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    
    QTTime newQTTime = [GetQtMovie(movieId) duration];
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);

    long long time = (newQTTime.timeValue * 1000.0f) / newQTTime.timeScale;
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);

    
    return time;
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT jdouble JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_getMovieWidth
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    NSLog(@"Width func");
    fflush(stderr);
    NSLog(@"Number of movies found: %lu", (unsigned long)[movies count]);
    fflush(stderr);
    NSSize sourceSize = [[[GetQtMovie(movieId) movieAttributes] valueForKey:@"QTMovieNaturalSizeAttribute"] sizeValue];
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);

    
    return sourceSize.width;
    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT jboolean JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_isPlaying
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    if([GetQtMovie(movieId) rate] != 0) {
        JNF_CHECK_AND_RETHROW_EXCEPTION(env);
        return false;
    }
    else
        return true;

    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT jfloat JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_getRate
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    return [GetQtMovie(movieId) rate];
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);

    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_setRate
(JNIEnv *env, jobject canvas, jfloat rate, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    [GetQtMovie(movieId) setRate:rate];
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);

    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT jfloat JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_getFPS
(JNIEnv *env, jobject canvas, jfloat rate, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    return [[fpses objectAtIndex:movieId] floatValue];
    
//    for (QTTrack *track in [GetQtMovie(movieId) tracks]) {
//        QTMedia *media = [track media];
//        if (![media hasCharacteristic:QTMediaCharacteristicHasVideoFrameRate])
//            continue;
//        QTTime duration = [(NSValue *)[media attributeForKey:QTMediaDurationAttribute] QTTimeValue];
//        long sampleCount = [(NSNumber *)[media attributeForKey:QTMediaSampleCountAttribute] longValue];
//        return sampleCount * ((NSTimeInterval)duration.timeScale / (NSTimeInterval)duration.timeValue);
//    }
//    return 0.0;
    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

/*
 * Class:     org_datavyu_plugins_qtkitplayer_QTKitPlayer
 * Method:    addNativeCoreAnimationLayer
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_addNativeCoreAnimationLayer
(JNIEnv *env, jobject canvas, jstring path, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
	
JNF_COCOA_ENTER(env);
    
    if(!movies) {
        NSLog(@"Initializing movies array");
        movies = [[NSMutableArray alloc] init];
        fpses = [[NSMutableArray alloc] init];
    }
    if(!movieLayers) {
        NSLog(@"Initializing movielayers array");

        movieLayers = [[NSMutableArray alloc] init];
    }
	
    // get the AWT
	JAWT awt;
    awt.version = JAWT_VERSION_1_4 | JAWT_MACOSX_USE_CALAYER; // opt into the CALayer model
    jboolean result = JAWT_GetAWT(env, &awt);
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    if (result == JNI_FALSE) return; // CALayer support unavailable prior to Java for Mac OS X 10.6 Update 4
    
    NSString* filePath = ConvertToNSString(env, path);
    
    // get the drawing surface
    JAWT_DrawingSurface *ds = awt.GetDrawingSurface(env, canvas);
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    assert(ds != NULL);
    
    // lock the drawing surface
    jint lock = ds->Lock(ds); 
	JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    assert((lock & JAWT_LOCK_ERROR) == 0);
    
    // get the drawing surface info
    JAWT_DrawingSurfaceInfo *dsi = ds->GetDrawingSurfaceInfo(ds);
	JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    
	// Check DrawingSurfaceInfo. This can be NULL on Mac OS X if the native 
	// component heirachy has not been made visible yet on the AppKit thread.
    if (dsi != NULL) {
		// create and attach the layer on the AppKit thread
		[JNFRunLoop performOnMainThreadWaiting:YES withBlock:^(){
			// attach the "root layer" to the AWT Canvas surface layers
			id <JAWT_SurfaceLayers> surfaceLayers = (id <JAWT_SurfaceLayers>)dsi->platformInfo;
            NSLog(@"Attaching movie");
            MovieLayer* movieLayer =[[MovieLayer alloc] initWithFile: filePath];
            JNF_CHECK_AND_RETHROW_EXCEPTION(env);
            NSLog(@"Movie attached");

			surfaceLayers.layer = movieLayer;
		}];
		
        // free the DrawingSurfaceInfo
        ds->FreeDrawingSurfaceInfo(dsi);
        JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    }
    
    NSLog(@"Number of movies found: %lu", (unsigned long)[movies count]);

	
    // unlock the drawing surface
    ds->Unlock(ds); 
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    
    // free the drawing surface
    awt.FreeDrawingSurface(ds);
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
	
JNF_COCOA_EXIT(env);
	
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_stop
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    NSLog(@"Stop");
    [GetQtMovie(movieId) stop];
    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_play
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    NSLog(@"Play");
    [GetQtMovie(movieId) play];
    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_setTime
(JNIEnv *env, jobject canvas, jlong time, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    NSLog(@"Setting time %lld", (long long)time);
    if(time == 0) {
        time = 1;
    }
//    QTTime t = QTMakeTime((long long)time, (long)time);
    
    QTTime newQTTime = [GetQtMovie(movieId) currentTime];
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    newQTTime.timeValue = ((long long)time / 1000.0f) * newQTTime.timeScale;
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    [GetQtMovie(movieId) setCurrentTime:newQTTime];
//    if(time == 0) {
    
//    }
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
     long long t = (newQTTime.timeValue * 1000.0f) / newQTTime.timeScale;
    if(time == 1) {
        
        [GetQtMovie(movieId) stepForward];
        
        newQTTime = [GetQtMovie(movieId) currentTime];
        
        while(t > 20) {
            NSLog(@"Reported time %lld", t);
            t = (newQTTime.timeValue * 1000.0f) / newQTTime.timeScale;
            [GetQtMovie(movieId) stepBackward];
        }
    }

    
    JNF_CHECK_AND_RETHROW_EXCEPTION(env);
    
    
    NSLog(@"Reported time %lld", t);
    
//    [movie stop];
//    [movie setCurrentTime:t];
//    [movie play];
    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_setVolume
(JNIEnv *env, jobject canvas, jfloat volume, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    NSLog(@"Setting volume %f", (float)volume);
    [GetQtMovie(movieId) setVolume:(float)volume];
    
    //    [movie stop];
    //    [movie setCurrentTime:t];
    //    [movie play];
    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

JNIEXPORT void JNICALL Java_org_datavyu_plugins_qtkitplayer_QTKitPlayer_release
(JNIEnv *env, jobject canvas, jint movieId) {
#ifdef JAWT_MACOSX_USE_CALAYER // Java for Mac OS X 10.6 Update 4 or later required
    
    JNF_COCOA_ENTER(env);
    
    NSLog(@"Releasing movie");
    [GetQtMovie(movieId) stop];
    [movies replaceObjectAtIndex:GetQtMovie(movieId) withObject:nil];
    
    JNF_COCOA_EXIT(env);
    
#endif // JAWT_MACOSX_USE_CALAYER
}

//@implementation QTKitPlayer
//
//- (id) initWithFile:(NSString*)file {
//    
//}
//
//- (void) play {
//    
//}
//
//- (void) seek {
//    
//}
//
//- (void) stop {
//    
//}
//
//@end


// a "root layer" that contains three sample layers (Quartz Composistion, OpenGL, and QuickTime)
@implementation MovieLayer

- (id) initWithFile:(NSString*)file {
	self = [super init];
	if (!self) return nil;
	
	// instance handles it's own layout
	self.layoutManager = self;
	
//	// create a Quartz Composistion layer from the app bundle
//	NSString *compositionPath = [[NSBundle mainBundle] pathForResource:@"Clouds" ofType:@"qtz"];
//	CALayer *qcLayer = [QCCompositionLayer compositionLayerWithFile:compositionPath];
//	[self addSublayer:qcLayer];
//	
//	// do some custom GL drawing
//	RotatingSquareGLLayer *caGLLayer = [RotatingSquareGLLayer layer];
//	caGLLayer.asynchronous = YES;
//	[self addSublayer:caGLLayer];
	
	// play a QuickTime movie from the app bundle
//	movie = [QTMovie movieNamed:@"Sample.mov" error:nil];
    

    
    
//    NSError* error = nil;
    NSLog(@"Opening %@", file);
    file = [file stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    
    // Open the movie in editing mode, get the FPS, close it, open it in playback mode
    QTMovie *movie = nil;
    NSError *error = nil;
    NSDictionary *attributes =
    [NSDictionary dictionaryWithObjectsAndKeys:
     [NSURL URLWithString:file], QTMovieURLAttribute,
     [NSNumber numberWithBool:NO], QTMovieLoopsAttribute,
     [NSNumber numberWithBool:NO], QTMovieOpenForPlaybackAttribute,
     [NSNumber numberWithBool:NO], QTMovieOpenAsyncOKAttribute,
     nil];
    
    movie = [[QTMovie alloc] initWithAttributes:attributes
                                          error:&error];
    
    // Get the FPS
    float fps = 0.0;
    for (QTTrack *track in [movie tracks]) {
        QTMedia *media = [track media];
        if (![media hasCharacteristic:QTMediaCharacteristicHasVideoFrameRate])
            continue;
        QTTime duration = [(NSValue *)[media attributeForKey:QTMediaDurationAttribute] QTTimeValue];
        long sampleCount = [(NSNumber *)[media attributeForKey:QTMediaSampleCountAttribute] longValue];
        fps = sampleCount * ((NSTimeInterval)duration.timeScale / (NSTimeInterval)duration.timeValue);
    }
    NSSize sourceSize = [[[movie movieAttributes] valueForKey:@"QTMovieNaturalSizeAttribute"] sizeValue];
    NSLog(@"Normal Height: %f, Width: %f", sourceSize.height, sourceSize.width);
    NSLog(@"Releasing movie");
    [movie release];
    
    attributes =
    [NSDictionary dictionaryWithObjectsAndKeys:
     [NSURL URLWithString:file], QTMovieURLAttribute,
     [NSNumber numberWithBool:NO], QTMovieLoopsAttribute,
     [NSNumber numberWithBool:YES], QTMovieOpenForPlaybackAttribute,
     [NSNumber numberWithBool:NO], QTMovieOpenAsyncOKAttribute,
//     [NSNumber numberWithBool:YES], QTMoviePlaysAllFramesAttribute,
     nil];
    NSLog(@"Opening movie in playback mode");
    movie = [[QTMovie alloc] initWithAttributes:attributes
                                          error:&error];
    

    if(movie == nil || error)
        NSLog(@"Error: %@ %@", error, [error userInfo]);
//    NSLog(@"Movie loaded: %lld, %ld", [movie duration].timeValue, [movie duration].timeScale);
    sourceSize = [[[movie movieAttributes] valueForKey:@"QTMovieNaturalSizeAttribute"] sizeValue];
    NSLog(@"Playback Height: %f, Width: %f", sourceSize.height, sourceSize.width);

//    NSLog@(@"Movie width: %ld, height: %ld", [movie ])
    NSLog(@"Movie opened in playback mode");
    QTMovieLayer* qtMovieLayer = [QTMovieLayer layerWithMovie:movie];
    NSLog(@"Layer created");
    [self addSublayer:qtMovieLayer];
    NSLog(@"Added sublayer");
    [movies addObject:movie];
    NSLog(@"Added movie");
    [movieLayers addObject:self];
    [fpses addObject:[NSNumber numberWithFloat:fps]];
    
    NSLog(@"Returning, movie loaded");
	return self;
}

- (void)layoutSublayersOfLayer:(CALayer *)layer {
	NSArray *sublayers = layer.sublayers;
	
	CGRect layerFrame = layer.frame;
	CGFloat width = layerFrame.size.width / [sublayers count];
	CGFloat height = layerFrame.size.height;
	
	// layout left to right
	CGFloat x = 0;
	for(CALayer *child in sublayers) {
		child.frame = CGRectMake(x, 0, width, height);
		x += width;
	}
}

- (void)loadMovie:(NSString*)file {
//    [self removeAllAnimations];
//    for (CALayer *layer in [[self.sublayers copy] autorelease]) {
//        [layer removeFromSuperlayer];
//    }
//    movie = [QTMovie movieWithURL: [NSURL URLWithString:filePath] error:nil];
//    QTMovieLayer* qtMovieLayer = [QTMovieLayer layerWithMovie:movie];
//    [self addSublayer:qtMovieLayer];

}

@end



