import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { LocationTypeDetailComponent } from 'app/entities/location-type/location-type-detail.component';
import { LocationType } from 'app/shared/model/location-type.model';

describe('Component Tests', () => {
  describe('LocationType Management Detail Component', () => {
    let comp: LocationTypeDetailComponent;
    let fixture: ComponentFixture<LocationTypeDetailComponent>;
    const route = ({ data: of({ locationType: new LocationType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [LocationTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LocationTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load locationType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.locationType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
